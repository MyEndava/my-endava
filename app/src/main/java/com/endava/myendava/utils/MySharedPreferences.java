package com.endava.myendava.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;

    public static final String MY_SHARED_PREFS_NAME = "MyEndavaSharedPrefs";

    private String IS_LOGGED_AS_GUEST = "is_logged_in_as_guest";
    private String EMAIL = "email";
    private String UP_NAVIGATION_ID = "up_navigation_id";

    @Inject
    public MySharedPreferences(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public boolean isLoggedInAsGuest() {
        return mSharedPreferences.getBoolean(IS_LOGGED_AS_GUEST, false);
    }

    public void setUserAsGuest() {
        mSharedPreferences.edit().putBoolean(IS_LOGGED_AS_GUEST, true).apply();
    }

    public void setUserAsEmployee() {
        mSharedPreferences.edit().putBoolean(IS_LOGGED_AS_GUEST, false).apply();
    }

    public void setUserEmail(String email) {
        mSharedPreferences.edit().putString(EMAIL, email).apply();
    }

    public String getUserEmail() {
        return mSharedPreferences.getString(EMAIL, "");
    }

    public void setUpNavigationId(int fragmentId) {
        mSharedPreferences.edit().putInt(UP_NAVIGATION_ID, fragmentId).apply();
    }

    public int getUpNavigationId() {
        return mSharedPreferences.getInt(UP_NAVIGATION_ID, 0);
    }

    public void resetUpNavigationId(){
        mSharedPreferences.edit().putInt(UP_NAVIGATION_ID, 0).apply();
    }
}
