package com.endava.myendava.di.activities;

import com.endava.myendava.activities.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {MainModule.class})
public interface MainComponent {

    void inject(MainActivity activity);

    @Subcomponent.Builder
    interface Builder {
        MainComponent.Builder mainBuilder(MainModule module);

        MainComponent build();
    }
}
