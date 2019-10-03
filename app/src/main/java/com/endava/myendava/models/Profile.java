package com.endava.myendava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("companyname")
    @Expose
    private String companyname;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags;
    @SerializedName("arrivalDate")
    @Expose
    private String arrivalDate;
    @SerializedName("registrationToken")
    @Expose
    private Integer registrationToken;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("authenticationToken")
    @Expose
    private Integer authenticationToken;
    @SerializedName("badge_id")
    @Expose
    private Integer badgeId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("departureDate")
    @Expose
    private String departureDate;
    @SerializedName("location")
    @Expose
    private String location;

    public Profile() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(Integer registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(Integer authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public Integer getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Integer badgeId) {
        this.badgeId = badgeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
