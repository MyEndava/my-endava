package com.endava.myendava.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.presenters.activities.SplashPresenter;
import com.endava.myendava.views.activities.SplashView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends AppCompatActivity implements SplashView {

    @Inject
    SplashPresenter mPresenter;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mUnbinder = ButterKnife.bind(this);
        setupModule();
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getApplication();
        locator.getSplashComponent(this).inject(this);
        informPresenterViewReady();
    }

    private void informPresenterViewReady() {
        mPresenter.viewReady();
    }

    @Override
    public void showSignInScreen() {
        SignInActivity.start(this);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
