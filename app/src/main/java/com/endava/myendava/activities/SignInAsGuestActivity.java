package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.presenters.activities.SignInAsGuestPresenter;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.views.activities.SignInAsGuestView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignInAsGuestActivity extends AppCompatActivity implements SignInAsGuestView {

    @Inject
    SignInAsGuestPresenter mPresenter;

    @Inject
    MySharedPreferences mSharedPreferences;

    @BindView(R.id.sign_in_button)
    Button mSignInButton;

    private Unbinder mUnbinder;

    public static void start(SignInActivity activity) {
        activity.startActivity(new Intent(activity, SignInAsGuestActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_as_guest);
        mUnbinder = ButterKnife.bind(this);
        setupModule();
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getApplication();
        locator.getSignInAsGuestComponent(this).inject(this);
        informPresenterViewReady();
    }

    private void informPresenterViewReady() {
        mPresenter.viewReady();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSignInButton.setOnClickListener(v -> showMainScreen());
    }

    private void showMainScreen() {
        mSharedPreferences.setUserAsGuest();
        MainGuestActivity.start(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
