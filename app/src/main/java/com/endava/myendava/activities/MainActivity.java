package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.ProfileFragment;
import com.endava.myendava.R;
import com.endava.myendava.Tag;
import com.endava.myendava.TagsFragment;
import com.endava.myendava.UsersActivity;
import com.endava.myendava.fragments.DashboardFragment;
import com.endava.myendava.fragments.FaqFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements ProfileFragment.OnProfileFragmentInteractionListener, FaqFragment.OnFaqFragmentInteractionListener,
        TagsFragment.OnTagsFragmentInteractionListener, DashboardFragment.OnDashboardFragmentInteractionListener {

    @BindView(R.id.navigation_view)
    BottomNavigationView mNavigationView;

    private Unbinder mUnbinder;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        DashboardFragment.newInstance()).commit();
                return true;
            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        ProfileFragment.newInstance()).commit();
                return true;
            case R.id.navigation_tags:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        TagsFragment.newInstance()).commit();
                return true;
            case R.id.navigation_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        FaqFragment.newInstance()).commit();
                return true;
        }
        return false;
    };

    public static void start(SignInActivity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setSelectedItemId(R.id.navigation_dashboard);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onSkillSelected(Tag tag) {
        Intent intent = new Intent(MainActivity.this, UsersActivity.class);
        intent.putExtra(UsersActivity.ARG_TAG, tag);
        startActivity(intent);
    }

    @Override
    public void onAddSkillClicked() {
        Intent intent = new Intent(MainActivity.this, SuggestTagActivity.class);
        startActivity(intent);
    }
}
