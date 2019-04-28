package com.orobator.stackoverflow.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.orobator.stackoverflow.R;
import com.orobator.stackoverflow.databinding.ListItemQuestionBinding;
import com.orobator.stackoverflow.viewmodel.QuestionViewModel;

import java.util.Collections;
import java.util.List;

import static com.orobator.stackoverflow.view.QuestionsRecyclerViewAdapter.QuestionViewHolder;

public class QuestionsRecyclerViewAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
  private List<QuestionViewModel> viewModels = Collections.emptyList();

  @NonNull @Override
  public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ListItemQuestionBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.list_item_question, parent, false);
    return new QuestionViewHolder(binding);
  }

  @Override public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
  }

  @Override public int getItemCount() {
    return viewModels.size();
  }

  public void updateViewModels(List<QuestionViewModel> viewModels) {
    this.viewModels = viewModels;
    notifyDataSetChanged();
  }

  static public class QuestionViewHolder extends RecyclerView.ViewHolder {
    private final ListItemQuestionBinding binding;

    public QuestionViewHolder(@NonNull ListItemQuestionBinding binding) {
      super(binding.getRoot());

      this.binding = binding;
    }

      public void bind(QuestionView question) {
          binding.setQ(question);

      binding.chipGroup.removeAllViews();
          for (String tag : question.tags) {
        Chip chip = new Chip(itemView.getContext());
        chip.setText(tag);
        chip.setChipBackgroundColorResource(R.color.orange_100);
        binding.chipGroup.addView(chip);
      }
    }
  }
}

