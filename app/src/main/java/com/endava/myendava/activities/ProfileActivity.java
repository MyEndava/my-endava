package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.R;
import com.endava.myendava.fragments.ProfileFragment;
import com.endava.myendava.models.Tag;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.OnProfileFragmentInteractionListener {

    public static final String ARG_EMAIL = "arg_email";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (savedInstanceState == null) {
            String email = getIntent().getStringExtra(ARG_EMAIL);
            getSupportFragmentManager().beginTransaction().add(R.id.profile_container,
                    ProfileFragment.newInstance(email, false)).commit();
        }
    }

    @Override
    public void onSkillSelected(Tag tag, int navigationId) {
        Intent intent = new Intent(ProfileActivity.this, UsersActivity.class);
        intent.putExtra(UsersActivity.ARG_TAG, tag);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }
}
