package com.endava.myendava.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag implements Parcelable {

    @SerializedName("tag_category")
    @Expose
    private String category;

    @SerializedName("tag_subcategory")
    @Expose
    private String subcategory;

    @SerializedName("tag_data")
    @Expose
    private String tagData;

    @SerializedName("tag_id")
    @Expose
    private Integer tagId;

    @SerializedName("user_tag_id")
    @Expose
    private Integer userTagId;

    @SerializedName("tag_name")
    @Expose
    private String tagName;

    @SerializedName("tag_description")
    @Expose
    private String tagDescription;

    public Tag() {

    }

    public Tag(String category, String subcategory, String tagData, Integer tagId, Integer userTagId,
               String tagName, String tagDescription) {
        this.category = category;
        this.subcategory = subcategory;
        this.tagData = tagData;
        this.tagId = tagId;
        this.userTagId = userTagId;
        this.tagName = tagName;
        this.tagDescription = tagDescription;
    }

    protected Tag(Parcel in) {
        category = in.readString();
        subcategory = in.readString();
        tagData = in.readString();
        if (in.readByte() == 0) {
            tagId = null;
        } else {
            tagId = in.readInt();
        }
        if (in.readByte() == 0) {
            userTagId = null;
        } else {
            userTagId = in.readInt();
        }
        tagName = in.readString();
        tagDescription = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getTagData() {
        return tagData;
    }

    public void setTagData(String tagData) {
        this.tagData = tagData;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getUserTagId() {
        return userTagId;
    }

    public void setUserTagId(Integer userTagId) {
        this.userTagId = userTagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", tagData='" + tagData + '\'' +
                ", tagId=" + tagId + '\'' +
                ", userTagId=" + userTagId + '\'' +
                ", tagName='" + tagName + '\'' +
                ", tagDescription='" + tagDescription + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(category);
        parcel.writeString(subcategory);
        parcel.writeString(tagData);
        if (tagId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(tagId);
        }
        if (userTagId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(userTagId);
        }
        parcel.writeString(tagName);
        parcel.writeString(tagDescription);
    }
}
