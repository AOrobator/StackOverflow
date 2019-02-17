package com.orobator.stackoverflow.viewmodel;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import com.orobator.stackoverflow.interactors.QuestionsUseCases;
import com.orobator.stackoverflow.rx.AppSchedulers;
import com.orobator.stackoverflow.view.QuestionsViewState;
import io.reactivex.disposables.Disposable;
import java.util.List;

public class QuestionsViewModel extends ViewModel {
  private QuestionsUseCases useCases;
  private AppSchedulers schedulers;
  @Nullable
  private Disposable disposable;
  public final ObservableField<QuestionsViewState> viewState = new ObservableField<>();

  public QuestionsViewModel(QuestionsUseCases useCases, AppSchedulers schedulers) {
    this.useCases = useCases;
    this.schedulers = schedulers;
  }

  @Override protected void onCleared() {
    super.onCleared();

    if (disposable != null) {
      disposable.dispose();
      disposable = null;
    }
  }

  public void loadQuestions() {
    disposable = useCases.getHotQuestions()
        .subscribeOn(schedulers.io)
        .observeOn(schedulers.main)
        .doOnSubscribe(disposable -> viewState.set(new QuestionsViewState(true, false)))
        .subscribe(
            this::onGetQuestionsSuccess,
            this::onGetQuestionsError);
  }

  private void onGetQuestionsSuccess(List<QuestionViewModel> questions) {
    viewState.set(new QuestionsViewState(false, false));
  }

  private void onGetQuestionsError(Throwable throwable) {
    viewState.set(new QuestionsViewState(false, true));
  }
}
