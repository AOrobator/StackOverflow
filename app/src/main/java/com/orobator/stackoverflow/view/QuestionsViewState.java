package com.orobator.stackoverflow.view;

public class QuestionsViewState {
  public final boolean isLoading;
  public final boolean isEmpty;

  public QuestionsViewState(boolean isLoading, boolean isEmpty) {
    this.isLoading = isLoading;
    this.isEmpty = isEmpty;
  }
}
