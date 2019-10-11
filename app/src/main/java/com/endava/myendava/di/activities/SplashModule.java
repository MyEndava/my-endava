package com.endava.myendava.di.activities;

import com.endava.myendava.activities.SplashActivity;

import dagger.Module;

@Module
public class SplashModule {

    private final SplashActivity mActivity;

    public SplashModule(SplashActivity activity) {
        mActivity = activity;
    }
}