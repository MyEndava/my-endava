package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.utils.MySharedPreferences;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignInActivity extends AppCompatActivity {

    @Inject
    MySharedPreferences mSharedPreferences;

    @BindView(R.id.sign_in_button)
    Button mSignInButton;

    @BindView(R.id.sign_in_as_guest_button)
    Button mSignInAsGuestButton;

    @BindView(R.id.email)
    EditText mUserEmailEditText;

    @BindView(R.id.password)
    EditText mPasswordEditText;

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
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getApplication();
        locator.getSignInComponent(this).inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSignInButton.setOnClickListener(v -> showMainScreen());
        mSignInAsGuestButton.setOnClickListener(v -> showSignInAsGuestScreen());
    }

    private void showSignInAsGuestScreen() {
        SignInAsGuestActivity.start(this);
        finish();
    }

    private void showMainScreen() {
        mSharedPreferences.setUserAsEmployee();
        mSharedPreferences.setUserEmail(mUserEmailEditText.getText().toString());
        MainActivity.start(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
