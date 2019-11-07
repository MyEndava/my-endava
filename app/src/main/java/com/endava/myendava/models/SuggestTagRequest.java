package com.endava.myendava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuggestTagRequest {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private int category;
    @SerializedName("subcategory")
    @Expose
    private int subcategory;

    public SuggestTagRequest(String tagName, String tagDescription, int tagCategory, int tagSubcategory) {
        name = tagName;
        description = tagDescription;
        category = tagCategory;
        subcategory = tagSubcategory;
    }
}
