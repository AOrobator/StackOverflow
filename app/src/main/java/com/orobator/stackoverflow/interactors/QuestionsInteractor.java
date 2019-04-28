package com.orobator.stackoverflow.interactors;

import com.orobator.stackoverflow.client.questions.model.QuestionsResponse;
import com.orobator.stackoverflow.client.questions.repository.QuestionsApi.Order;
import com.orobator.stackoverflow.client.questions.repository.QuestionsApi.Sort;
import com.orobator.stackoverflow.client.questions.repository.QuestionsRepository;
import com.orobator.stackoverflow.viewmodel.QuestionViewModel;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.List;

public class QuestionsInteractor implements QuestionsUseCases {
  private QuestionsRepository repository;
  private HtmlParser parser;

  public QuestionsInteractor(QuestionsRepository repository, HtmlParser parser) {
    this.repository = repository;
    this.parser = parser;
  }

    public static <T, R> List<R> mapList(List<T> list, Mapper<T, R> mapper) {
    ArrayList<R> mappedList = new ArrayList<>(list.size());
    for (T item : list) {
      mappedList.add(mapper.map(item));
    }
    return mappedList;
  }

    @Override
    public Single<List<QuestionViewModel>> getHotQuestions(int page, int pageSize) {
    return repository
            .getQuestions(page, pageSize, Order.DESC, Sort.HOT)
        .map(QuestionsResponse::getItems)
            .map(questions -> mapList(questions,
                question -> new QuestionViewModel(
                        parser.parse(question.getTitle()),
                        question.getScore(),
                        question.getTags())));
  }

  interface Mapper<A, B> {
    B map(A a);
  }

  public interface HtmlParser {
    String parse(String html);
  }
}
