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

    private final Context context;
    private final Map<String, List<Tag>> tagsMap;
    private final OnChipClickedListener listener;

    public ChipsAdapter(Context context, Map<String, List<Tag>> tagsMap, OnChipClickedListener listener) {
        this.context = context;
        this.tagsMap = tagsMap;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chips,
                parent, false);
        return new TagsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TagsViewHolder tagsViewHolder = (TagsViewHolder) holder;
        tagsViewHolder.tagsGroupTextView.setText(getTagGroupByPosition(position));
        for (Tag tag : getTagsByPosition(position)) {
            tagsViewHolder.createChip(getTagGroupByPosition(position), tag, listener);
        }
    }

    @Override
    public int getItemCount() {
        return tagsMap.size();
    }

    private String getTagGroupByPosition(int position) {
        return (String) tagsMap.keySet().toArray()[position];
    }

    private List<Tag> getTagsByPosition(int position) {
        return tagsMap.get((tagsMap.keySet().toArray())[position]);
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
            chip.setTransitionName("chip_title_to_toolbar_title_transition");
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onChipClicked(tag);
                }
            });
            chipGroup.addView(chip);
        }
    }
}
