package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.fragments.DashboardFragment;
import com.endava.myendava.fragments.FaqFragment;
import com.endava.myendava.fragments.ProfileFragment;
import com.endava.myendava.fragments.TagsFragment;
import com.endava.myendava.models.Tag;
import com.endava.myendava.utils.EmailType;
import com.endava.myendava.utils.MySharedPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements ProfileFragment.OnProfileFragmentInteractionListener, FaqFragment.OnFaqFragmentInteractionListener,
        TagsFragment.OnTagsFragmentInteractionListener, DashboardFragment.OnDashboardFragmentInteractionListener {

    @Inject
    MySharedPreferences mSharedPreferences;

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
                        ProfileFragment.newInstance(EmailType.OWN_EMAIL.getType(), true)).commit();
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
        setupModule();
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setSelectedItemId(R.id.navigation_dashboard);
        int upFragmentId = mSharedPreferences.getUpNavigationId();
        if (upFragmentId != 0) {
            navigate(upFragmentId);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getApplication();
        locator.getMainComponent(this).inject(this);
    }

    @Override
    public void onSkillSelected(Tag tag, int fragmentID) {
        mSharedPreferences.setUpNavigationId(fragmentID);
        Intent intent = new Intent(MainActivity.this, UsersActivity.class);
        intent.putExtra(UsersActivity.ARG_TAG, tag);
        startActivity(intent);
    }

    @Override
    public void onAddSkillClicked() {
        Intent intent = new Intent(MainActivity.this, SuggestTagActivity.class);
        startActivity(intent);
    }

    private void navigate(int fragmentId) {
        mNavigationView.setSelectedItemId(fragmentId);
        switch (fragmentId) {
            case R.id.navigation_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        DashboardFragment.newInstance()).commit();
                return;
            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        ProfileFragment.newInstance(EmailType.OWN_EMAIL.getType(), true)).commit();
                return;
            case R.id.navigation_tags:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        TagsFragment.newInstance()).commit();
                return;
            case R.id.navigation_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        FaqFragment.newInstance()).commit();
        }
    }
}
