package com.orobator.stackoverflow.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import com.orobator.stackoverflow.R;
import com.orobator.stackoverflow.databinding.ListItemQuestionBinding;

public class QuestionsPagedAdapter
        extends PagedListAdapter<QuestionView, QuestionsRecyclerViewAdapter.QuestionViewHolder> {
    public static final DiffUtil.ItemCallback<QuestionView> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<QuestionView>() {

                @Override
                public boolean areItemsTheSame(QuestionView oldItem, QuestionView newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(QuestionView oldItem, QuestionView newItem) {
                    return true;
                }
            };

    protected QuestionsPagedAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public QuestionsRecyclerViewAdapter.QuestionViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemQuestionBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.list_item_question, parent, false);
        return new QuestionsRecyclerViewAdapter.QuestionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull
                                         QuestionsRecyclerViewAdapter.QuestionViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
