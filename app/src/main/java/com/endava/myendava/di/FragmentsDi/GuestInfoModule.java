package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.GuestInfoFragment;
import com.endava.myendava.presenters.fragments.GuestInfoPresenter;
import com.endava.myendava.views.fragments.GuestInfoView;

import dagger.Module;
import dagger.Provides;

@Module
public class GuestInfoModule {

    private final GuestInfoFragment mFragment;

    public GuestInfoModule(GuestInfoFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    GuestInfoPresenter provideGuestInfoPresenter() {
        GuestInfoPresenter presenter = new GuestInfoPresenter();
        presenter.setView((GuestInfoView) mFragment);
        return presenter;
    }
}
