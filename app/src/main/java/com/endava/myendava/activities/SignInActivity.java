package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.viewmodels.UsersViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignInActivity extends AppCompatActivity {

    @Inject
    MySharedPreferences mSharedPreferences;

    @Inject
    UsersViewModel mUsersViewModel;

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

    private Unbinder mUnbinder;
    private boolean isMailValid;


    public static final String INVALID_EMAIL_MESSAGE = "Invalid email";

    public static void start(SplashActivity activity) {
        activity.startActivity(new Intent(activity, SignInActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mUnbinder = ButterKnife.bind(this);
        setTransparentStatusBar();
        setupModule();
    }

    private void setTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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

    private void handleSignIn() {
        if (!isMailValid) {
            Toast.makeText(this, INVALID_EMAIL_MESSAGE, Toast.LENGTH_SHORT).show();
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
            mUsersViewModel.checkUserEmail(email).observe(this, valid -> {
                this.isMailValid = valid.booleanValue();
                handleSignIn();
            });
        } else Toast.makeText(this, INVALID_EMAIL_MESSAGE, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
