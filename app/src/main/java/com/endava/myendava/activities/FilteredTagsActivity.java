package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.endava.myendava.R;
import com.endava.myendava.fragments.FilteredTagsFragment;

public class FilteredTagsActivity extends AppCompatActivity implements FilteredTagsFragment.OnFilteredTagsFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_tags);
        setupActionBar();
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onTagAdded() {
        onSupportNavigateUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(FilteredTagsActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

}
