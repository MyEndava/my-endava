package com.endava.myendava.di.activitiesDi;

import com.endava.myendava.activities.SplashActivity;
import com.endava.myendava.presenters.activities.SplashPresenter;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.views.activities.SplashView;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    private final SplashActivity mActivity;

    public SplashModule(SplashActivity activity) {
        mActivity = activity;
    }

    @Provides
    SplashPresenter provideSplashPresenter(MySharedPreferences mySharedPreferences) {
        SplashPresenter presenter = new SplashPresenter(mySharedPreferences);
        presenter.setView((SplashView) mActivity);
        return presenter;
    }
}