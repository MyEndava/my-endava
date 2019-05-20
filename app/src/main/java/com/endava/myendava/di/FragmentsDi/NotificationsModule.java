package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.NotificationsFragment;
import com.endava.myendava.presenters.fragments.NotificationsPresenter;
import com.endava.myendava.views.fragments.NotificationsView;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationsModule {

    private final NotificationsFragment mFragment;

    public NotificationsModule(NotificationsFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    NotificationsPresenter provideNotificationsPresenter() {
        NotificationsPresenter presenter = new NotificationsPresenter();
        presenter.setView((NotificationsView) mFragment);
        return presenter;
    }
}