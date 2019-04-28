package com.orobator.stackoverflow.client.questions.repository;

import com.orobator.stackoverflow.client.questions.model.QuestionsResponse;
import com.orobator.stackoverflow.client.questions.repository.QuestionsApi.Order;
import com.orobator.stackoverflow.client.questions.repository.QuestionsApi.Sort;
import io.reactivex.Single;

import static com.orobator.stackoverflow.client.questions.repository.QuestionsApi.STACK_OVERFLOW_SITE;

public class QuestionsDownloader implements QuestionsRepository {
  private QuestionsApi questionsApi;

  public QuestionsDownloader(QuestionsApi questionsApi) {
    this.questionsApi = questionsApi;
  }

  @Override
  public Single<QuestionsResponse> getQuestions(int page, int pageSize, Order order, Sort sort) {
      return questionsApi.getQuestions(page, pageSize, order.val, sort.val, STACK_OVERFLOW_SITE);
  }

    @Override
    public Single<QuestionsResponse> getHotQuestions(int page, int pageSize) {
        return getQuestions(page, pageSize, Order.DESC, Sort.HOT);
  }
}
