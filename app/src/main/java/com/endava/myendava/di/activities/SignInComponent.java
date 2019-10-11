package com.endava.myendava.di.activities;

import com.endava.myendava.activities.SignInActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {SignInModule.class})
public interface SignInComponent {

    void inject(SignInActivity activity);

    @Subcomponent.Builder
    interface Builder {
        SignInComponent.Builder signInBuilder(SignInModule module);

        SignInComponent build();
    }
}
