package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.endava.myendava.R;
import com.endava.myendava.fragments.ProfileFragment;
import com.endava.myendava.models.Tag;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProfileActivity extends BaseFullScreenActivity implements ProfileFragment.OnProfileFragmentInteractionListener {

    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.bottom_view)
    View bottomView;

    public static final String ARG_EMAIL = "arg_email";

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        unbinder = ButterKnife.bind(this);
        setFullScreen();
        setNavigationBar();
        if (savedInstanceState == null) {
            String email = getIntent().getStringExtra(ARG_EMAIL);
            getSupportFragmentManager().beginTransaction().add(R.id.profile_container,
                    ProfileFragment.newInstance(email, false)).commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onTagsSearch(List<Tag> tagList, int navigationId) {
        Intent intent = new Intent(ProfileActivity.this, UsersActivity.class);
        intent.putExtra(UsersActivity.ARG_TAG_LIST, (Serializable) tagList);
        startActivity(intent);
    }

    @Override
    protected ConstraintLayout getContainer() {
        return container;
    }

    @Override
    protected int getBottomViewId() {
        return bottomView.getId();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
