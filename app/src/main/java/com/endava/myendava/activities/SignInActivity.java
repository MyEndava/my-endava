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
import com.endava.myendava.viewmodels.SignInViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignInActivity extends BaseActivity {

    public static final String INVALID_EMAIL_MESSAGE = "Invalid email";

    public static final String TERMS_AND_COND_NOT_CHECKED_MESSAGE = "You must agree with the terms and conditions";

    @Inject
    MySharedPreferences mSharedPreferences;

    @Inject
    SignInViewModel mSignInViewModel;

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
        hideProgressBar(mProgressBar);
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
    }


    private void showSignInAsGuestScreen() {
        SignInAsGuestActivity.start(this);
        finish();
    }

    private void handleSignIn(boolean isMailValid) {
        if (!mTermsAndCondCheckBox.isChecked()) {
            Toast.makeText(this, TERMS_AND_COND_NOT_CHECKED_MESSAGE, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isMailValid) {
            Toast.makeText(this, INVALID_EMAIL_MESSAGE, Toast.LENGTH_SHORT).show();
            return;
        }
        mSignInViewModel.isLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(mProgressBar);
            } else {
                hideProgressBar(mProgressBar);
            }
        });
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
            mSignInViewModel.checkUserEmail(email).observe(this, valid -> {
                handleSignIn(valid.booleanValue());
            });
        } else {
            Toast.makeText(this, INVALID_EMAIL_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        mSignInViewModel.getError().observe(this, this::displayError);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
