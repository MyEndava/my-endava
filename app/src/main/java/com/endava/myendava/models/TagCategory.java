package com.endava.myendava.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex.Graur@endava.com at 10/25/2019
 */
public class TagCategory implements Parcelable {

    @SerializedName("tag_category_id")
    @Expose
    private int id;
    @SerializedName("tag_category_name")
    @Expose
    private String title;
    private boolean isSelected;

    public TagCategory() {

    }

    protected TagCategory(Parcel in) {
        title = in.readString();
    }

    public static final Creator<TagCategory> CREATOR = new Creator<TagCategory>() {
        @Override
        public TagCategory createFromParcel(Parcel in) {
            return new TagCategory(in);
        }

        @Override
        public TagCategory[] newArray(int size) {
            return new TagCategory[size];
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
        return "TagCategory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
