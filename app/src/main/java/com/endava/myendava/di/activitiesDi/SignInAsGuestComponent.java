package com.endava.myendava.di.activitiesDi;

import com.endava.myendava.activities.SignInAsGuestActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {SignInAsGuestModule.class})
public interface SignInAsGuestComponent {

    void inject(SignInAsGuestActivity activity);

    @Subcomponent.Builder
    interface Builder {
        SignInAsGuestComponent.Builder signInAsGuestBuilder(SignInAsGuestModule module);

        SignInAsGuestComponent build();
    }
}