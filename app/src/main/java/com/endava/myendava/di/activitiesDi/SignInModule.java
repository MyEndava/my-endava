package com.endava.myendava.di.activitiesDi;

import com.endava.myendava.activities.SignInActivity;

import dagger.Module;

@Module
public class SignInModule {

    private final SignInActivity mActivity;

    public SignInModule(SignInActivity activity) {
        mActivity = activity;
    }
}
