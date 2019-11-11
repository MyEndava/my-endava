package com.endava.myendava.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagSubCategory implements Parcelable {

    @SerializedName("tag_subcategory_id")
    @Expose
    private int id;
    @SerializedName("tag_subcategory_name")
    @Expose
    private String title;

    public TagSubCategory() {

    }

    protected TagSubCategory(Parcel in) {
        title = in.readString();
    }

    public static final Creator<TagSubCategory> CREATOR = new Creator<TagSubCategory>() {
        @Override
        public TagSubCategory createFromParcel(Parcel in) {
            return new TagSubCategory(in);
        }

        @Override
        public TagSubCategory[] newArray(int size) {
            return new TagSubCategory[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @Override
    public String toString() {
        return "TagSubCategory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
