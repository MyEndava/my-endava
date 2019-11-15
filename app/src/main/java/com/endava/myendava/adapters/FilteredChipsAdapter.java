package com.endava.myendava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.models.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilteredChipsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Tag> tags;
    private final OnTagClickListener onTagClickListener;

    public FilteredChipsAdapter(Context context, List<Tag> tagList, OnTagClickListener onTagClickListener) {
        this.context = context;
        this.tags = tagList;
        this.onTagClickListener = onTagClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filtered_chips, parent, false);
        return new FilteredChipsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FilteredChipsViewHolder) holder).bind(tags);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setData(List<Tag> tags) {
        this.tags = tags;
        notifyDataSetChanged();
    }

    class FilteredChipsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tags_chip_group)
        ChipGroup chipGroup;
        @BindView(R.id.placeholder)
        TextView placeHolder;

        FilteredChipsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final List<Tag> tags) {
            removeChips();
            if (tags.isEmpty()) {
                placeHolder.setVisibility(View.VISIBLE);
            } else {
                placeHolder.setVisibility(View.GONE);
            }
            for (Tag tag : tags) {
                createChip(tag, onTagClickListener);
            }
        }

        void createChip(final Tag tag, final OnTagClickListener onChipClickedListener) {
            Chip chip = new Chip(chipGroup.getContext());
            AtomicBoolean isSelected = new AtomicBoolean(true);
            chip.setText(tag.getTagName());
            chip.setChipBackgroundColorResource(R.color.white);
            chip.setTextColor(context.getColor(R.color.technical_text_color));
            chip.setTextSize(12);
            chip.setCheckedIcon(null);
            chip.setChipStrokeWidth(2.0f);
            chip.setOnClickListener(v -> {
                onChipClickedListener.onTagClicked(tag);
                setChipColors(chip, isSelected.get());
                isSelected.set(!isSelected.get());
            });
            List<Tag> mSelectedTags = onTagClickListener.getSelectedTags();
            setChipColors(chip, mSelectedTags.contains(tag));
            isSelected.set(!mSelectedTags.contains(tag));
            chipGroup.addView(chip);
        }

        void setChipColors(Chip chip, boolean isSelected) {
            if (isSelected) {
                chip.setTextColor(context.getColor(R.color.white));
                chip.setChipBackgroundColorResource(R.color.primary);
                chip.setChipStrokeColorResource(R.color.primary);
            } else {
                chip.setTextColor(context.getColor(R.color.technical_text_color));
                chip.setChipBackgroundColorResource(R.color.white);
                chip.setChipStrokeColorResource(R.color.technical_text_color);
            }
        }

        void removeChips() {
            chipGroup.removeAllViews();
        }
    }

    public interface OnTagClickListener {

        void onTagClicked(Tag tag);

        List<Tag> getSelectedTags();
    }
}
