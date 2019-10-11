package com.endava.myendava.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.activities.FilteredTagsActivity;
import com.endava.myendava.listeners.OnChipClickedListener;
import com.endava.myendava.listeners.OnProfileEditedListener;
import com.endava.myendava.models.Profile;
import com.endava.myendava.models.Tag;
import com.endava.myendava.utils.TagColorManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChipsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final Context context;
    private final String email;
    private final Profile profile;
    private final Map<String, List<Tag>> tagsMap;
    private final OnChipClickedListener listener;
    private final OnProfileEditedListener onEditListener;
    private boolean isProfileEditable;

    public ChipsAdapter(Context context, String email, Boolean isEditable, Profile profile, Map<String, List<Tag>> tagsMap,
                        OnChipClickedListener listener, OnProfileEditedListener onEditListener) {
        this.context = context;
        this.profile = profile;
        this.tagsMap = tagsMap;
        this.listener = listener;
        this.onEditListener = onEditListener;
        this.isProfileEditable = isEditable;
        this.email = email;
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
            ((TagsViewHolder) holder).bind(tagsMap);
            TagsViewHolder tagsViewHolder = (TagsViewHolder) holder;
            tagsViewHolder.tagsGroupTextView.setText(getTagGroupByPosition(position));
            tagsViewHolder.addTagTextView.setTextColor(context.getResources().getColor(R.color.gray5));
            tagsViewHolder.removeChips();
            for (Tag tag : getTagsByPosition(position)) {
                tagsViewHolder.createChip(getTagGroupByPosition(position), tag, listener);
                switch (tag.getSubcategory()) {
                    case "Project":
                        tagsViewHolder.tagHolderLayout.setBackground(context.getResources().getDrawable(R.drawable.about_me_shape));
                        tagsViewHolder.tagsGroupTextView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        tagsViewHolder.addTagTextView.setVisibility(View.GONE);
                        break;
                    case "Technical":
                        tagsViewHolder.tagHolderLayout.setBackground(context.getResources().getDrawable(R.drawable.technical_shape));
                        tagsViewHolder.tagsGroupTextView.setTextColor(context.getResources().getColor(R.color.white));
                        break;
                    case "Soft":
                        tagsViewHolder.tagHolderLayout.setBackground(context.getResources().getDrawable(R.drawable.soft_shape));
                        tagsViewHolder.tagsGroupTextView.setTextColor(context.getResources().getColor(R.color.white));
                }
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


        @BindView(R.id.tags_group_text_view)
        TextView tagsGroupTextView;

        @BindView(R.id.tags_chip_group)
        ChipGroup chipGroup;

        @BindView(R.id.add_button)
        TextView addTagTextView;

        @BindView(R.id.layout)
        ConstraintLayout tagHolderLayout;


        TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Map<String, List<Tag>> tagsMap) {
            if (tagsMap != null) {
                addTagTextView.setOnClickListener(view -> {
                    Intent intent = new Intent(context, FilteredTagsActivity.class);
                    context.startActivity(intent);
                });
            }
        }

        public void removeChips() {
            chipGroup.removeAllViews();
        }

        void createChip(final String tagGroup, final Tag tag, final OnChipClickedListener listener) {
            Chip chip = new Chip(chipGroup.getContext());
            chip.setText(tag.getTagName());
            chip.setChipBackgroundColorResource(TagColorManager.getBackgroundColor(tagGroup));
            int color = context.getColor(TagColorManager.getTextChipColor(tagGroup));
            chip.setTextColor(color);
            chip.setCloseIconEnabled(!"Project".equals(tagGroup) && isProfileEditable);
            chip.setOnCloseIconClickListener(view -> {
                List<Tag> tags = getTagsByPosition(getAdapterPosition());
                for (Tag currentTag : tags) {
                    if (currentTag.getTagId().equals(tag.getTagId())) {
                        tags.remove(currentTag);
                        notifyDataSetChanged();
                    }
                }
            });
            chip.setOnClickListener(v -> listener.onChipClicked(tag));
            if ("Skill".equals(tag.getCategory())) {
                chip.setCloseIcon(context.getDrawable(R.drawable.ic_close));
                chip.setCloseIconVisible(isProfileEditable);
                chip.setCloseIconTintResource(R.color.secondary);
            }
            chipGroup.addView(chip);
        }
    }

    public class DescriptionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.edit_about_me)
        TextView editButton;

        @BindView(R.id.description_text_view)
        EditText descriptionTextView;

        DescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Profile profile) {

            if (profile != null) {
                descriptionTextView.setText(profile.getDescription());
                if (profile.getEmail() != null && !profile.getEmail().equals(email)) {
                    editButton.setVisibility(View.GONE);
                }
                editButton.setOnClickListener(view -> {
                    if (editButton.getText().equals(context.getResources().getString(R.string.edit_text_press))) {
                        descriptionTextView.setEnabled(true);
                        descriptionTextView.requestFocus();
                        isProfileEditable = true;
                        onEditListener.onEditClicked(true);
                        editButton.setText(context.getResources().getString(R.string.save_text_press));
                    } else {
                        descriptionTextView.setEnabled(false);
                        editButton.setText(context.getResources().getString(R.string.edit_text_press));
                        isProfileEditable = false;
                        onEditListener.onEditClicked(false);
                    }
                    notifyDataSetChanged();
                });

            }
        }
    }
}