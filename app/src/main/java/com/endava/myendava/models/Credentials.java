package com.endava.myendava.models;

import com.google.gson.annotations.SerializedName;

public class Credentials {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public Credentials() {

    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
