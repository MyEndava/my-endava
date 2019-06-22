package com.endava.myendava.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.ProfileFragment;
import com.endava.myendava.R;

public class ProfileActivity extends AppCompatActivity {

    public static final String ARG_EMAIL = "arg_email";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (savedInstanceState == null) {
            String email = getIntent().getStringExtra(ARG_EMAIL);
            getSupportFragmentManager().beginTransaction().add(R.id.profile_container,
                    ProfileFragment.newInstance(email)).commit();
        }
    }
}
