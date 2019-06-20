package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.GuestInfoFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {GuestInfoModule.class})
public interface GuestInfoComponent {

    void inject(GuestInfoFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        GuestInfoComponent.Builder guestInfoBuilder(GuestInfoModule module);

        GuestInfoComponent build();
    }
}
