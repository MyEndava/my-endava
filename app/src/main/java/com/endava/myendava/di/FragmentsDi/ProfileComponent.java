package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.ProfileFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {ProfileModule.class})
public interface ProfileComponent {

    void inject(ProfileFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        ProfileComponent.Builder profileBuilder(ProfileModule module);

        ProfileComponent build();
    }
}