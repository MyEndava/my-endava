package com.endava.myendava.di.activities;

import com.endava.myendava.activities.SplashActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {SplashModule.class})
public interface SplashComponent {

    void inject(SplashActivity activity);

    @Subcomponent.Builder
    interface Builder {
        SplashComponent.Builder splashBuilder(SplashModule module);

        SplashComponent build();
    }
}