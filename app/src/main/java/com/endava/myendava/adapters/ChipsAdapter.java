package com.endava.myendava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.listeners.OnChipClickedListener;
import com.endava.myendava.listeners.OnProfileEditedListener;
import com.endava.myendava.models.Profile;
import com.endava.myendava.models.Tag;
import com.endava.myendava.models.TagSubCategory;
import com.endava.myendava.utils.TagColorManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChipsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final Context context;
    private final String email;
    private Profile profile;
    private Map<String, List<Tag>> tagsMap;
    private List<TagSubCategory> tagSubCategories = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    private List<Chip> chips = new ArrayList<>();
    private final OnChipClickedListener onChipClickedListener;
    private final OnProfileEditedListener onEditedListener;
    private boolean isProfileEditable;

    public ChipsAdapter(Context context, String email, Boolean isEditable, Profile profile, Map<String, List<Tag>> tagsMap,
                        OnChipClickedListener onChipClickedListener, OnProfileEditedListener onEditedListener) {
        this.context = context;
        this.profile = profile;
        this.tagsMap = tagsMap;
        this.onChipClickedListener = onChipClickedListener;
        this.onEditedListener = onEditedListener;
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
            String tagSubCategoryName = getTagGroupByPosition(position);
            ((TagsViewHolder) holder).bind(tagsMap, tagSubCategoryName);
            TagsViewHolder tagsViewHolder = (TagsViewHolder) holder;
            tagsViewHolder.removeChips();
            for (Tag tag : getTagsByPosition(position)) {
                tagsViewHolder.createChip(tagSubCategoryName, tag, onChipClickedListener);
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

    private void mapTagsWithSubcategories(List<Tag> tags, List<TagSubCategory> tagSubCategories) {
        if (!tags.isEmpty() && !tagSubCategories.isEmpty()) {
            Map<String, List<Tag>> tagsMap = new HashMap<>();
            if (profile.getEmail() != null && profile.getEmail().equals(email)) {
                for (TagSubCategory tagSubCategory : tagSubCategories) {
                    tagsMap.put(tagSubCategory.getTitle(), new ArrayList<>());
                }
            }
            for (Tag tag : tags) {
                addTagToMap(tagsMap, tag.getSubcategory(), tag);
            }
            this.tagsMap = tagsMap;
            notifyDataSetChanged();
        }
    }

    private void addTagToMap(Map<String, List<Tag>> tagsMap, String key, Tag tag) {
        List<Tag> tags;
        if (tagsMap.containsKey(key)) {
            tags = tagsMap.get(key);
        } else {
            tags = new ArrayList<>();
        }
        tags.add(tag);
        tagsMap.put(key, tags);
    }

    public void setData(Profile profile) {
        this.profile = profile;
        this.tags = profile.getTags();
        mapTagsWithSubcategories(tags, tagSubCategories);
    }

    public void setData(List<TagSubCategory> tagSubCategories) {
        this.tagSubCategories = tagSubCategories;
        mapTagsWithSubcategories(tags, tagSubCategories);
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tags_group_text_view)
        TextView tagsGroupTextView;
        @BindView(R.id.tags_chip_group)
        ChipGroup chipGroup;
        @BindView(R.id.add_button)
        TextView addTagTextView;
        @BindView(R.id.tag_group_placeholder)
        TextView placeHolder;
        @BindView(R.id.layout)
        ConstraintLayout tagHolderLayout;

        TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Map<String, List<Tag>> tagsMap, String tagSubCategoryName) {
            if (tagsMap != null) {
                if (tagsMap.get(tagSubCategoryName).isEmpty()) {
                    placeHolder.setVisibility(View.VISIBLE);
                } else {
                    placeHolder.setVisibility(View.GONE);
                }
                setupTagCards(tagSubCategoryName);
                if (tagSubCategoryName.equals("Project")) {
                    addTagTextView.setVisibility(View.GONE);
                }
                if (profile.getEmail() != null && !profile.getEmail().equals(email)) {
                    addTagTextView.setVisibility(View.GONE);
                } else {
                    addTagTextView.setOnClickListener(view -> {
                        onEditedListener.onAddTag(tagSubCategoryName);
                    });
                }
            }
        }

        private void setupTagCards(String tagSubcategory) {
            tagsGroupTextView.setText(tagSubcategory);
            tagHolderLayout.setBackground(context.getResources().getDrawable(TagColorManager.getTagGroupBackgroundColor(tagSubcategory)));
            tagsGroupTextView.setTextColor(context.getResources().getColor(TagColorManager.getChipBackgroundColor(tagSubcategory)));
            addTagTextView.setTextColor(context.getResources().getColor(TagColorManager.getChipBackgroundColor(tagSubcategory)));
            placeHolder.setTextColor(context.getResources().getColor(TagColorManager.getChipBackgroundColor(tagSubcategory)));
        }

        public void removeChips() {
            chipGroup.removeAllViews();
        }

        void createChip(final String tagGroup, final Tag tag, final OnChipClickedListener onChipClickedListener) {
            Chip chip = new Chip(chipGroup.getContext());
            chip.setText(tag.getTagName());
            chip.setChipBackgroundColorResource(TagColorManager.getChipBackgroundColor(tagGroup));
            int color = context.getColor(TagColorManager.getChipTextColor(tagGroup));
            chip.setTextColor(color);
            chip.setTextSize(12);
            chip.setCloseIconEnabled(!"Project".equals(tagGroup) && isProfileEditable);
            chip.setOnCloseIconClickListener(view -> {
                List<Tag> tags = getTagsByPosition(getAdapterPosition());
                for (Tag currentTag : tags) {
                    if (currentTag.getTagId().equals(tag.getTagId())) {
                        displayConfirmationDialog(tag);
                    }
                }
            });
            chip.setOnClickListener(v -> onChipClickedListener.onChipClicked(tag));
            if (!"Project".equals(tag.getSubcategory())) {
                chip.setCloseIcon(context.getDrawable(R.drawable.ic_close));
                chip.setCloseIconTintResource(TagColorManager.getChipTextColor(tagGroup));
            } else {
                chip.setCloseIcon(null);
            }
            chips.add(chip);
            chipGroup.addView(chip);
        }
    }

    private void displayConfirmationDialog(Tag tag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_AlertDialogRemoveTag);
        builder.setMessage(R.string.title_alert_dialog_remove_tag);
        builder.setPositiveButton(R.string.button_text_yes_dialog_remove_tag, (dialog, which) ->
                onEditedListener.onTagRemove(tag));
        builder.setNegativeButton(R.string.button_text_cancel_dialog_remove_tag, null);
        AlertDialog dialog = builder.create();
        dialog.show();
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
                makeViewsEditable(isProfileEditable);
                if (profile.getEmail() != null && !profile.getEmail().equals(email)) {
                    editButton.setVisibility(View.GONE);
                }
                editButton.setOnClickListener(view -> {
                    String description = descriptionTextView.getText().toString();
                    if (editButton.getText().equals(context.getResources().getString(R.string.edit_text_press))) {
                        makeViewsEditable(true);
                        descriptionTextView.requestFocus();
                        isProfileEditable = true;
                        onEditedListener.onEditClicked(true, description);
                    } else {
                        makeViewsEditable(false);
                        isProfileEditable = false;
                        onEditedListener.onEditClicked(false, description);
                    }
                    makeChipsRemovable(isProfileEditable);
                });
            }
        }

        private void makeChipsRemovable(boolean isEditable) {
            for (Chip chip : chips) {
                chip.setCloseIconVisible(isEditable);
            }
        }

        private void makeViewsEditable(boolean isEditable) {
            descriptionTextView.setEnabled(isEditable);
            if (isEditable) {
                editButton.setText(context.getResources().getString(R.string.save_text_press));
            } else {
                editButton.setText(context.getResources().getString(R.string.edit_text_press));
            }
        }
    }
}