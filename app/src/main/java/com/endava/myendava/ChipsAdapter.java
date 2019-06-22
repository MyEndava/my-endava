package com.endava.myendava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.Map;

public class ChipsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final Context context;
    private final Profile profile;
    private final Map<String, List<Tag>> tagsMap;
    private final OnChipClickedListener listener;

    public ChipsAdapter(Context context, Profile profile, Map<String, List<Tag>> tagsMap,
                        OnChipClickedListener listener) {
        this.context = context;
        this.profile = profile;
        this.tagsMap = tagsMap;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chips,
                    parent, false);
            return new TagsViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_description,
                    parent, false);
            return new DescriptionViewHolder(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DescriptionViewHolder) {
            ((DescriptionViewHolder) holder).bind(profile);
        } else if (holder instanceof TagsViewHolder) {
            TagsViewHolder tagsViewHolder = (TagsViewHolder) holder;
            tagsViewHolder.tagsGroupTextView.setText(getTagGroupByPosition(position));
            for (Tag tag : getTagsByPosition(position)) {
                tagsViewHolder.createChip(getTagGroupByPosition(position), tag, listener);
            }
        }
    }

    @Override
    public int getItemCount() {
        return tagsMap.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private String getTagGroupByPosition(int position) {
        return (String) tagsMap.keySet().toArray()[position - 1];
    }

    private List<Tag> getTagsByPosition(int position) {
        return tagsMap.get((tagsMap.keySet().toArray())[position - 1]);
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder {

        TextView tagsGroupTextView;
        ChipGroup chipGroup;

        TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            tagsGroupTextView = itemView.findViewById(R.id.tags_group_text_view);
            chipGroup = itemView.findViewById(R.id.tags_chip_group);
        }

        void createChip(final String tagGroup, final Tag tag, final OnChipClickedListener listener) {
            Chip chip = new Chip(chipGroup.getContext());
            chip.setText(tag.getTagName());
            chip.setChipBackgroundColorResource(TagColorManager.getColor(tagGroup));
            chip.setTextColor(context.getResources().getColor(android.R.color.white));
            chip.setOnClickListener(v -> listener.onChipClicked(tag));
            if ("Skill".equals(tag.getCategory())) {
                chip.setCloseIcon(context.getDrawable(R.drawable.ic_close));
                chip.setCloseIconVisible(true);
                chip.setCloseIconTintResource(R.color.white);
            }
            chipGroup.addView(chip);
        }
    }

    public class DescriptionViewHolder extends RecyclerView.ViewHolder {

        TextView descriptionTextView;

        DescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
        }

        void bind(final Profile profile) {
            if (profile != null) {
                descriptionTextView.setText(profile.getDescription());
            }
        }
    }
}
