package com.orobator.stackoverflow.client.questions;

import io.reactivex.Single;

public interface QuestionsRepository {

  Single<QuestionsResponse> getQuestions(
      int page,
      int pageSize,
      QuestionsApi.Order order,
      QuestionsApi.Sort sort
  );
}
