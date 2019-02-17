package com.orobator.stackoverflow.view;

import java.util.Objects;

public class QuestionsViewState {
  public final boolean isLoading;
  public final boolean isEmpty;
  public final boolean hasError;

  public QuestionsViewState(boolean isLoading, boolean isEmpty, boolean hasError) {
    this.isLoading = isLoading;
    this.isEmpty = isEmpty;
    this.hasError = hasError;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    QuestionsViewState that = (QuestionsViewState) o;
    return isLoading == that.isLoading &&
        isEmpty == that.isEmpty &&
        hasError == that.hasError;
  }

  @Override
  public int hashCode() {
    return Objects.hash(isLoading, isEmpty, hasError);
  }
}
