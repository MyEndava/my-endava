package com.endava.myendava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.endava.myendava.OnChipClickedListener;
import com.endava.myendava.R;
import com.endava.myendava.Tag;
import com.endava.myendava.TagColorManager;
import com.endava.myendava.adapters.model.FaqItem;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaqRecyclerAdapter extends RecyclerView.Adapter<FaqRecyclerAdapter.FaqViewHolder> {

    private List<FaqItem> mItemsList;
    private OnChipClickedListener mListener;
    private RecyclerView mRecyclerView;

    public FaqRecyclerAdapter(List<FaqItem> faqItems, OnChipClickedListener listener) {
        mItemsList = faqItems;
        mListener = listener;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        FaqItem item = mItemsList.get(holder.getAdapterPosition());
        holder.mAnswerTextView.setText(item.getAnswer());
        holder.mQuestionTextView.setText(item.getQuestion());
        holder.mAnswerContainer.setVisibility(item.isExpanded() ? View.VISIBLE : View.GONE);
        holder.mExpandArrowImageView.setRotation(item.isExpanded() ? 180 : 0);
        holder.mTagsChipGroup.removeAllViews();
        for (Tag tag : item.getTags()) {
            holder.mTagsChipGroup.addView(createChip(tag, holder.mTagsChipGroup.getContext()));
        }
        holder.mQuestionContainer.setOnClickListener(view -> {
            if (!item.isExpanded()) {
                holder.mAnswerContainer.setVisibility(View.VISIBLE);
                item.setExpanded(true);
            } else {
                holder.mAnswerContainer.setVisibility(View.GONE);
                item.setExpanded(false);
            }
            TransitionManager.beginDelayedTransition(mRecyclerView);
            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    private Chip createChip(Tag tag, Context context) {
        Chip chip = new Chip(context);
        chip.setText(tag.getTitle());
        chip.setChipBackgroundColorResource(TagColorManager.getColor(tag.getTagGroup().getName()));
        chip.setTextColor(context.getResources().getColor(android.R.color.white));
        chip.setOnClickListener(v -> mListener.onChipClicked(tag));
        return chip;
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    class FaqViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.faq_question_container)
        ConstraintLayout mQuestionContainer;
        @BindView(R.id.faq_answer_container)
        ConstraintLayout mAnswerContainer;
        @BindView(R.id.question_text_view)
        TextView mQuestionTextView;
        @BindView(R.id.answer_text_view)
        TextView mAnswerTextView;
        @BindView(R.id.expand_arrow)
        ImageView mExpandArrowImageView;
        @BindView(R.id.tags_chip_group)
        ChipGroup mTagsChipGroup;

        FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
