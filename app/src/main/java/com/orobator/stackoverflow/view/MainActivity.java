package com.orobator.stackoverflow.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.orobator.stackoverflow.R;
import com.orobator.stackoverflow.databinding.ActivityMainBinding;
import com.orobator.stackoverflow.viewmodel.QuestionsViewModel;
import com.orobator.stackoverflow.viewmodel.QuestionsViewModelFactory;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
  @Inject
  QuestionsViewModelFactory viewModelFactory;
  private ActivityMainBinding binding;
  private QuestionsViewModel viewModel;
  private QuestionsRecyclerViewAdapter adapter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidInjection.inject(this);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    adapter = new QuestionsRecyclerViewAdapter();
    binding.questionsRecyclerView.setAdapter(adapter);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(QuestionsViewModel.class);
    viewModel.questionViewModels.observe(this, viewModels -> adapter.updateViewModels(viewModels));
    binding.setVm(viewModel);
  }

  @Override
  protected void onStart() {
    super.onStart();

    viewModel.loadQuestions();
  }
}
