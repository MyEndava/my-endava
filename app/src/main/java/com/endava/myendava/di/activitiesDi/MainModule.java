package com.endava.myendava.di.activitiesDi;

import com.endava.myendava.activities.MainActivity;
import com.endava.myendava.presenters.activities.MainPresenter;
import com.endava.myendava.views.activities.MainView;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final MainActivity mActivity;

    public MainModule(MainActivity activity) {
        mActivity = activity;
    }

    @Provides
    MainPresenter provideMainPresenter() {
        MainPresenter presenter = new MainPresenter();
        presenter.setView((MainView) mActivity);
        return presenter;
    }
}
