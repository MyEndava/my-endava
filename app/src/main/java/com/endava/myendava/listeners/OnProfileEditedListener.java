package com.endava.myendava.listeners;

import com.endava.myendava.models.Tag;

public interface OnProfileEditedListener {

    void onEditClicked(boolean isEditable, String description);

    void onTagRemove(Tag tag);

    void onAddTag(String subcategory);
}