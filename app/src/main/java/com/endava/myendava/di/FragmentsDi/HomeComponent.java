package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.HomeFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {HomeModule.class})
public interface HomeComponent {

    void inject(HomeFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        HomeComponent.Builder homeBuilder(HomeModule module);

        HomeComponent build();
    }
}
