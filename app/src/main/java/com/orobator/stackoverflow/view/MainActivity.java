package com.orobator.stackoverflow.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;
import com.orobator.stackoverflow.R;
import com.orobator.stackoverflow.databinding.ActivityMainBinding;
import com.orobator.stackoverflow.interactors.QuestionsDataSourceFactory;
import com.orobator.stackoverflow.viewmodel.QuestionsViewModel;
import com.orobator.stackoverflow.viewmodel.QuestionsViewModelFactory;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    QuestionsViewModelFactory viewModelFactory;
    @Inject
    QuestionsDataSourceFactory dataSourceFactory;
  private ActivityMainBinding binding;
  private QuestionsViewModel viewModel;
    private QuestionsPagedAdapter adapter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidInjection.inject(this);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
      adapter = new QuestionsPagedAdapter();
    binding.questionsRecyclerView.setAdapter(adapter);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(QuestionsViewModel.class);
    binding.setVm(viewModel);

      PagedList.Config config = new PagedList.Config.Builder()
              .setPageSize(10)
              .setEnablePlaceholders(false)
              .setInitialLoadSizeHint(10)
              .build();

      Observable<PagedList<QuestionView>> pagedListObservable =
              new RxPagedListBuilder<>(dataSourceFactory, config)
                      .setFetchScheduler(Schedulers.io())
                      .setNotifyScheduler(AndroidSchedulers.mainThread())
                      .setInitialLoadKey(1)
                      .buildObservable();

      pagedListObservable.subscribe(pagedList -> adapter.submitList(pagedList));
  }

  @Override
  protected void onStart() {
    super.onStart();

    viewModel.loadQuestions();
  }
}
