package com.orobator.stackoverflow.client.questions.repository;

import com.orobator.stackoverflow.client.questions.model.QuestionsResponse;
import io.reactivex.Single;

public interface QuestionsRepository {

  Single<QuestionsResponse> getQuestions(
      int page,
      int pageSize,
      QuestionsApi.Order order,
      QuestionsApi.Sort sort
  );

  Single<QuestionsResponse> getHotQuestions(int page, int pageSize);
}
