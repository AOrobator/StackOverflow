package com.orobator.stackoverflow.interactors;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import com.orobator.stackoverflow.client.questions.model.Question;
import com.orobator.stackoverflow.client.questions.repository.QuestionsRepository;
import com.orobator.stackoverflow.rx.AppSchedulers;
import com.orobator.stackoverflow.view.QuestionView;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

import static com.orobator.stackoverflow.interactors.QuestionsInteractor.mapList;

public class QuestionsDataSource extends PageKeyedDataSource<Integer, QuestionView> {
    private QuestionsRepository repository;
    private CompositeDisposable disposable;
    private AppSchedulers schedulers;

    public QuestionsDataSource(QuestionsRepository repository, AppSchedulers schedulers) {
        this.repository = repository;
        this.schedulers = schedulers;
        disposable = new CompositeDisposable();
    }

    @Override
    public void loadBefore(
            @NonNull LoadParams<Integer> params,
            @NonNull LoadCallback<Integer, QuestionView> callback) {
        // ignored, since we only ever append to our initial load
        Log.d("QuestionsDataSource", "loadBefore() on thread " + Thread.currentThread().getName());
    }

    @Override
    public void loadInitial(
            @NonNull LoadInitialParams<Integer> params,
            @NonNull LoadInitialCallback<Integer, QuestionView> callback) {
        // triggered by a refresh, we better execute sync

        Log.d("QuestionsDataSource", "loadInitial() on thread " + Thread.currentThread().getName());
        disposable.add(
                repository
                        .getHotQuestions(1, params.requestedLoadSize)
                        .subscribe(
                                response -> {
                                    Integer nextKey = null;
                                    if (response.hasMore()) {
                                        nextKey = 2;
                                    }

                                    QuestionsInteractor.Mapper<Question, QuestionView> mapper =
                                            this::toQuestionView;
                                    List<QuestionView> questionViews = mapList(response.getItems(), mapper);
                                    callback.onResult(questionViews, null, nextKey);
                                }));
    }

    QuestionView toQuestionView(Question q) {
        return new QuestionView(q.getQuestionId(), q.getTitle(), q.getScore() + "", q.getTags());
    }

    @Override
    public void loadAfter(
            @NonNull LoadParams<Integer> params,
            @NonNull LoadCallback<Integer, QuestionView> callback) {
        Log.d("QuestionsDataSource", "loadAfter() on thread " + Thread.currentThread().getName());
        disposable.add(
                repository.getHotQuestions(params.key, params.requestedLoadSize)
                        .subscribeOn(schedulers.io)
                        .observeOn(schedulers.main)
                        .subscribe(
                                response -> {
                                    Integer nextKey = null;
                                    if (response.hasMore()) {
                                        nextKey = params.key + 1;
                                    }

                                    QuestionsInteractor.Mapper<Question, QuestionView> mapper =
                                            this::toQuestionView;
                                    List<QuestionView> questionViews = mapList(response.getItems(), mapper);
                                    callback.onResult(questionViews, nextKey);
                                }
                        )
        );
    }
}
