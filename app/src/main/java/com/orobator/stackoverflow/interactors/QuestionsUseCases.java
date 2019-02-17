package com.orobator.stackoverflow.interactors;

import com.orobator.stackoverflow.viewmodel.QuestionViewModel;
import io.reactivex.Single;
import java.util.List;

public interface QuestionsUseCases {
  Single<List<QuestionViewModel>> getHotQuestions();
}
