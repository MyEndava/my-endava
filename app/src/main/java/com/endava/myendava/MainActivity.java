package com.endava.myendava;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.endava.myendava.fragments.FaqFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ProfileFragment.OnProfileFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                                ProfileFragment.newInstance()).commit();
                        return true;
                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                                TagsFragment.newInstance()).commit();
                        return true;
                    case R.id.navigation_tags:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                                FaqFragment.newInstance()).commit();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
    }

    @Override
    public void onTagSelected(Tag tag) {
        Intent intent = new Intent(this, UsersActivity.class);
        intent.putExtra(UsersActivity.ARG_TAG, tag);
        startActivity(intent);
    }
}
