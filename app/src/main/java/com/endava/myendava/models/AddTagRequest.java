package com.endava.myendava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTagRequest {

    @SerializedName("tag_id")
    @Expose
    private int tagId;
    @SerializedName("tag_type_id")
    @Expose
    private int tagTypeId;
    @SerializedName("email")
    @Expose
    private String email;

    public AddTagRequest(int tagId, int tagTypeId, String email) {
        this.tagId = tagId;
        this.tagTypeId = tagTypeId;
        this.email = email;
    }
}
