package com.orobator.stackoverflow.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import com.orobator.stackoverflow.interactors.QuestionsUseCases;
import com.orobator.stackoverflow.rx.AppSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.List;

public class QuestionsViewModel extends ViewModel {
  private boolean isLoading = false;
  private QuestionsUseCases useCases;
  private AppSchedulers schedulers;
  @Nullable
  private Disposable disposable;

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

  public boolean isLoading() {
    return isLoading;
  }

  public void loadQuestions() {
    disposable = useCases.getHotQuestions()
        .subscribeOn(schedulers.io)
        .observeOn(schedulers.main)
        .doOnSubscribe(disposable -> isLoading = true)
        .doAfterTerminate(() -> isLoading = false)
        .subscribe(
            this::onGetQuestionsSuccess,
            this::onGetQuestionsError);
  }

  private void onGetQuestionsSuccess(List<QuestionViewModel> questions) {

  }

  private void onGetQuestionsError(Throwable throwable) {

  }
}
