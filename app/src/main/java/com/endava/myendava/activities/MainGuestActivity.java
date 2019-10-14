package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.R;
import com.endava.myendava.fragments.CalendarFragment;
import com.endava.myendava.fragments.FaqFragment;
import com.endava.myendava.fragments.GuestInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainGuestActivity extends AppCompatActivity {

    @BindView(R.id.navigation_view)
    BottomNavigationView mNavigationView;

    private Unbinder mUnbinder;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        CalendarFragment.newInstance()).commit();
                return true;
            case R.id.navigation_info:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        GuestInfoFragment.newInstance()).commit();
                return true;
            case R.id.navigation_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        FaqFragment.newInstance()).commit();
                return true;
        }
        return false;
    };

    public static void start(SignInAsGuestActivity activity) {
        activity.startActivity(new Intent(activity, MainGuestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);
        mUnbinder = ButterKnife.bind(this);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setSelectedItemId(R.id.navigation_dashboard);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
