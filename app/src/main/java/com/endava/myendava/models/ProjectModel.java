package com.endava.myendava.models;

import android.graphics.Bitmap;

public class ProjectModel {
    private String mName;
    private String mDescription;
    private Bitmap mImage;

    public ProjectModel() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        this.mImage = image;
    }
}
