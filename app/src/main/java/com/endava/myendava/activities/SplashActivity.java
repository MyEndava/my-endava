package com.endava.myendava.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.utils.MySharedPreferences;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends BaseActivity {

    @Inject
    MySharedPreferences mSharedPreferences;

    @BindView(R.id.logo)
    ImageView mLogo;

    private static final long SPLASH_LENGTH = 1500;

    private Unbinder mUnbinder;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mUnbinder = ButterKnife.bind(this);
        setupModule();
        mHandler = new Handler();
        startAnimation();
        resetUpNavigation();
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getApplication();
        locator.getSplashComponent(this).inject(this);
    }

    private void resetUpNavigation() {
        mSharedPreferences.resetUpNavigationId();
    }

    public void showSignInScreen() {
        SignInActivity.start(this);
        finish();
    }

    public void startAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.2f, 1f);
        animation.setDuration(600);
        mLogo.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(this::showSignInScreen, SPLASH_LENGTH);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
