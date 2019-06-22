package com.endava.myendava.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;

    @Inject
    public MySharedPreferences(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public boolean isLoggedInAsGuest(){
        return mSharedPreferences.getBoolean("is_logged_in_as_guest", false);
    }

    public void setUserAsGuest(){
        mSharedPreferences.edit().putBoolean("is_logged_in_as_guest", true).apply();
    }

    public void setUserAsEmployee(){
        mSharedPreferences.edit().putBoolean("is_logged_in_as_guest", false).apply();
    }

    public void setUserEmail(String email) {
        mSharedPreferences.edit().putString("user_email", email).apply();
    }

    public String getUserEmail() {
        return mSharedPreferences.getString("user_email", "");
    }
}
