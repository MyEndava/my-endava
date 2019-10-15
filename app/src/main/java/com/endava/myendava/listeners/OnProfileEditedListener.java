package com.endava.myendava.listeners;

import com.endava.myendava.models.Tag;

public interface OnProfileEditedListener {

    void onEditClicked(boolean isEditable);

    void onRemoveTag(Tag tag);
}