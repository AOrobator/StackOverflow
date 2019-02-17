package com.orobator.stackoverflow.viewmodel;

import java.util.Objects;

public class QuestionViewModel {
  public final String title;

  public QuestionViewModel(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    QuestionViewModel that = (QuestionViewModel) o;
    return Objects.equals(title, that.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title);
  }
}
