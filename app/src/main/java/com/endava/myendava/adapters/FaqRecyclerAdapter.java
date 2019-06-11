package com.endava.myendava.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.model.FaqItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaqRecyclerAdapter extends RecyclerView.Adapter<FaqRecyclerAdapter.FaqViewHolder> {

    private List<FaqItem> mItemsList;

    public FaqRecyclerAdapter(List<FaqItem> faqItems) {
        mItemsList = faqItems;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        FaqItem item = mItemsList.get(position);
        holder.mAnswerTextView.setText(item.getmAnswer());
        holder.mQuestionTextView.setText(item.getmQuestion());
        holder.mAnswerContainer.setVisibility(item.isExpanded() ? View.VISIBLE : View.GONE);
        holder.mExpandArrowImageView.setRotationX(item.isExpanded() ? 180 : 0);
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

        FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mQuestionContainer.setOnClickListener(view -> {
                FaqItem item = mItemsList.get(getAdapterPosition());
                if (item.isExpanded()) {
                    animateCollapseAnswer();
                    animateArrowRotation(180, 0);
                    item.setExpanded(false);

                } else {
                    animateExpandAnswer();
                    animateArrowRotation(0, 180);
                    item.setExpanded(true);
                }
            });
        }

        private void animateExpandAnswer() {
            mAnswerContainer.setVisibility(View.VISIBLE);
            mAnswerContainer.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int height = mAnswerContainer.getMeasuredHeight();
            ValueAnimator slideAnimator = ValueAnimator.ofInt(0, height)
                    .setDuration(200);
            slideAnimator.addUpdateListener(animator -> {
                mAnswerContainer.getLayoutParams().height = (Integer) animator.getAnimatedValue();
                mAnswerContainer.requestLayout();
            });
            AnimatorSet set = new AnimatorSet();
            set.play(slideAnimator);
            set.setInterpolator(new LinearInterpolator());
            set.start();
        }

        private void animateCollapseAnswer() {
            ValueAnimator slideAnimator = ValueAnimator.ofInt(mAnswerContainer.getMeasuredHeight(), 0)
                    .setDuration(200);
            slideAnimator.addUpdateListener(animator -> {
                mAnswerContainer.getLayoutParams().height = (Integer) animator.getAnimatedValue();
                mAnswerContainer.requestLayout();
            });
            slideAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mAnswerContainer.setVisibility(View.GONE);
                }
            });
            AnimatorSet set = new AnimatorSet();
            set.play(slideAnimator);
            set.setInterpolator(new LinearInterpolator());
            set.start();
        }

        private void animateArrowRotation(int startAngle, int endAngle) {
            Animation animation = new RotateAnimation(startAngle, endAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(200);
            animation.setFillAfter(true);
            animation.setInterpolator(new LinearInterpolator());
            mExpandArrowImageView.startAnimation(animation);
        }
    }
}
