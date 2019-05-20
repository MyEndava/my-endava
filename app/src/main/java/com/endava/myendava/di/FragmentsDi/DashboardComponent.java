package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.DashboardFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {DashboardModule.class})
public interface DashboardComponent {

    void inject(DashboardFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        DashboardComponent.Builder dashboardBuilder(DashboardModule module);

        DashboardComponent build();
    }
}
