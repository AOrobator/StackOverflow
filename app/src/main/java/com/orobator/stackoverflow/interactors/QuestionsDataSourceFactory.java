package com.orobator.stackoverflow.interactors;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import com.orobator.stackoverflow.client.questions.repository.QuestionsRepository;
import com.orobator.stackoverflow.rx.AppSchedulers;
import com.orobator.stackoverflow.view.QuestionView;

public class QuestionsDataSourceFactory extends DataSource.Factory<Integer, QuestionView> {
    private final QuestionsRepository repository;
    private final AppSchedulers schedulers;

    public QuestionsDataSourceFactory(
            QuestionsRepository repository, AppSchedulers schedulers) {
        this.repository = repository;
        this.schedulers = schedulers;
    }

    @NonNull
    @Override
    public DataSource<Integer, QuestionView> create() {
        return new QuestionsDataSource(repository, schedulers);
    }
}
