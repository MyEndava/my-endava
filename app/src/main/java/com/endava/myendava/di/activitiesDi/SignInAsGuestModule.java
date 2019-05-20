package com.endava.myendava.di.activitiesDi;

import com.endava.myendava.activities.SignInAsGuestActivity;
import com.endava.myendava.presenters.activities.SignInAsGuestPresenter;
import com.endava.myendava.views.activities.SignInAsGuestView;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInAsGuestModule {

    private final SignInAsGuestActivity mActivity;

    public SignInAsGuestModule(SignInAsGuestActivity activity) {
        mActivity = activity;
    }

    @Provides
    SignInAsGuestPresenter provideSignInAsGuestPresenter() {
        SignInAsGuestPresenter presenter = new SignInAsGuestPresenter();
        presenter.setView((SignInAsGuestView) mActivity);
        return presenter;
    }
}
