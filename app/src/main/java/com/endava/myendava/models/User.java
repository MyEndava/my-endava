package com.endava.myendava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("badge_id")
    @Expose
    private String badgeId;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("location")
    @Expose
    private String location;

    public User() {

    }

    public User(String firstName, String lastName, String description, String grade,
                String badgeId, String imageUrl, String companyName, String email, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.grade = grade;
        this.badgeId = badgeId;
        this.imageUrl = imageUrl;
        this.companyName = companyName;
        this.email = email;
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", grade='" + grade + '\'' +
                ", badgeId='" + badgeId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
