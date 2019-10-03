package com.endava.myendava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Project {

    @SerializedName("project_name")
    @Expose
    private String projectName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tag_id")
    @Expose
    private Integer tagId;
    @SerializedName("tag_name")
    @Expose
    private String tagName;
    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("tag_subcategory")
    @Expose
    private String tagSubcategory;
    @SerializedName("tag_category")
    @Expose
    private String tagCategory;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags;

    public Project() {

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTagSubcategory() {
        return tagSubcategory;
    }

    public void setTagSubcategory(String tagSubcategory) {
        this.tagSubcategory = tagSubcategory;
    }

    public String getTagCategory() {
        return tagCategory;
    }

    public void setTagCategory(String tagCategory) {
        this.tagCategory = tagCategory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", projectId=" + projectId +
                ", tagSubcategory='" + tagSubcategory + '\'' +
                ", tagCategory='" + tagCategory + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", tags=" + tags +
                '}';
    }
}
