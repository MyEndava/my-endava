package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.HomeFragment;
import com.endava.myendava.presenters.fragments.HomePresenter;
import com.endava.myendava.views.fragments.HomeView;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private final HomeFragment mFragment;

    public HomeModule(HomeFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    HomePresenter provideHomePresenter() {
        HomePresenter presenter = new HomePresenter();
        presenter.setView((HomeView) mFragment);
        return presenter;
    }
}