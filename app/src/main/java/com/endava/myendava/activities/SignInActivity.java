package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.viewmodels.LoginViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignInActivity extends BaseActivity {

    @Inject
    MySharedPreferences mSharedPreferences;
    @Inject
    LoginViewModel mLoginViewModel;

    @BindView(R.id.sign_in_button)
    Button mSignInButton;
    @BindView(R.id.sign_in_as_guest_button)
    Button mSignInAsGuestButton;
    @BindView(R.id.email)
    EditText mUserEmailEditText;
    @BindView(R.id.password)
    EditText mPasswordEditText;
    @BindView(R.id.forgot_password_button)
    Button mForgotPasswordButton;
    @BindView(R.id.terms_and_cond_checkbox)
    CheckBox mTermsAndCondCheckBox;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private Unbinder mUnbinder;

    public static void start(SplashActivity activity) {
        activity.startActivity(new Intent(activity, SignInActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mUnbinder = ButterKnife.bind(this);
        setupModule();
        setFullScreen();
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getApplication();
        locator.getSignInComponent(this).inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSignInButton.setOnClickListener(v -> validateEmail());
        mSignInAsGuestButton.setOnClickListener(v -> showSignInAsGuestScreen());
        mForgotPasswordButton.setOnClickListener(v -> handleForgotPassword());

        mLoginViewModel.isLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(mProgressBar);
            } else {
                hideProgressBar(mProgressBar);
            }
        });
        mLoginViewModel.getLoginSuccess().observe(this, this::handleLogin);
        mLoginViewModel.getError().observe(this, this::displayError);
    }

    private void showSignInAsGuestScreen() {
        SignInAsGuestActivity.start(this);
        finish();
    }

    private void handleLogin(boolean isMailValid) {
        if (!mTermsAndCondCheckBox.isChecked()) {
            Toast.makeText(this, R.string.terms_and_cond_unchecked_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isMailValid) {
            Toast.makeText(this, R.string.invalid_email_message, Toast.LENGTH_SHORT).show();
            return;
        }

        mSharedPreferences.setUserAsEmployee();
        mSharedPreferences.setUserEmail(mUserEmailEditText.getText().toString());
        MainActivity.start(this);
        finish();
    }

    private void handleForgotPassword() {
        Toast.makeText(this, "AHA", Toast.LENGTH_SHORT).show();
    }

    private void validateEmail() {
        String email = mUserEmailEditText.getText().toString();
        if (email.length() > 0) {
            mLoginViewModel.login(email);
        } else {
            Toast.makeText(this, R.string.invalid_email_message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mLoginViewModel.onCleared();
    }
}
