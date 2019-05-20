package com.endava.myendava.presenters.activities;

import android.os.Handler;

import com.endava.myendava.presenters.BasePresenter;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.views.activities.SplashView;

public class SplashPresenter extends BasePresenter<SplashView> {

    private static final long SPLASH_LENGTH = 2000;

    private MySharedPreferences mSharedPreferences;

    private Handler mHandler;

    public SplashPresenter(MySharedPreferences mySharedPreferences) {
        mSharedPreferences = mySharedPreferences;
        mHandler = new Handler();
    }

    @Override
    public void viewReady() {

    }

    public void onResume() {
        mHandler.postDelayed(() -> mViewRef.get().showSignInScreen(), SPLASH_LENGTH);
    }

    public void onPause() {
        mHandler.removeCallbacksAndMessages(null);
    }

}
