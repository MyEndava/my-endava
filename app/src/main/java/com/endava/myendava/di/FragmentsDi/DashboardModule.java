package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.DashboardFragment;
import com.endava.myendava.presenters.fragments.DashboardPresenter;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.views.fragments.DashboardView;

import dagger.Module;
import dagger.Provides;

@Module
public class DashboardModule {

    private final DashboardFragment mFragment;

    public DashboardModule(DashboardFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    DashboardPresenter provideDashboardPresenter(MySharedPreferences mySharedPreferences) {
        DashboardPresenter presenter = new DashboardPresenter(mySharedPreferences);
        presenter.setView((DashboardView) mFragment);
        return presenter;
    }
}