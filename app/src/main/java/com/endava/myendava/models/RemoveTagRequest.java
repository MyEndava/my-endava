package com.endava.myendava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveTagRequest {

    @SerializedName("user_tag_id")
    @Expose
    private int userTagId;

    public RemoveTagRequest(int userTagId) {
        this.userTagId = userTagId;
    }
}