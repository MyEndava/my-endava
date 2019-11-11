package com.endava.myendava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest {
    @SerializedName("user_email")
    @Expose
    private String email;
    @SerializedName("user_description")
    @Expose
    private String description;

    public UpdateProfileRequest(String userEmail, String userDescription) {
        email = userEmail;
        description = userDescription;
    }
}
