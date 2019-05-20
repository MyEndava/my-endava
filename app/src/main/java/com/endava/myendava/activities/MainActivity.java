package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.presenters.activities.MainPresenter;
import com.endava.myendava.views.activities.MainView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.nav_view)
    BottomNavigationView mNavigationView;

    private Unbinder mUnbinder;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        NavHostFragment nav = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        int currentFragment = nav.getNavController().getCurrentDestination().getId();
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (currentFragment != R.id.home_screen) {
                    Navigation.findNavController(findViewById(R.id.nav_host_fragment))
                            .navigate(R.id.action_global_home_screen);
                }
                return true;
            case R.id.navigation_dashboard:
                if (currentFragment != R.id.dashboard_screen) {
                    Navigation.findNavController(findViewById(R.id.nav_host_fragment))
                            .navigate(R.id.action_global_dashboard_screen);
                }
                return true;
            case R.id.navigation_notifications:
                if (currentFragment != R.id.notifications_screen) {
                    Navigation.findNavController(findViewById(R.id.nav_host_fragment))
                            .navigate(R.id.action_global_notifications_screen);
                }
                return true;
        }
        return false;
    };

    public static void start(SignInActivity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    public static void start(SignInAsGuestActivity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        setupModule();
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getApplication();
        locator.getMainComponent(this).inject(this);
        informPresenterViewReady();
    }

    private void informPresenterViewReady() {
        mPresenter.viewReady();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
