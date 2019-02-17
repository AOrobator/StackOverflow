package com.orobator.stackoverflow;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
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

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidInjection.inject(this);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(QuestionsViewModel.class);
    binding.setVm(viewModel);
  }

  @Override
  protected void onStart() {
    super.onStart();

    viewModel.loadQuestions();
  }
}
