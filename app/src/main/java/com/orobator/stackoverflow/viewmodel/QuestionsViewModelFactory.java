package com.orobator.stackoverflow.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.orobator.stackoverflow.interactors.QuestionsUseCases;
import com.orobator.stackoverflow.rx.AppSchedulers;

public class QuestionsViewModelFactory implements ViewModelProvider.Factory {
  private final QuestionsUseCases useCases;
  private final AppSchedulers schedulers;

  public QuestionsViewModelFactory(QuestionsUseCases useCases, AppSchedulers schedulers) {
    this.useCases = useCases;
    this.schedulers = schedulers;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    return (T) new QuestionsViewModel(useCases, schedulers);
  }
}
