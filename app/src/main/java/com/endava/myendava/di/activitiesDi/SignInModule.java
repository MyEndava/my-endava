package com.endava.myendava.di.activitiesDi;

import com.endava.myendava.activities.SignInActivity;
import com.endava.myendava.presenters.activities.SignInPresenter;
import com.endava.myendava.views.activities.SignInView;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {

    private final SignInActivity mActivity;

    public SignInModule(SignInActivity activity) {
        mActivity = activity;
    }

    @Provides
    SignInPresenter provideSignInPresenter() {
        SignInPresenter presenter = new SignInPresenter();
        presenter.setView((SignInView) mActivity);
        return presenter;
    }
}
