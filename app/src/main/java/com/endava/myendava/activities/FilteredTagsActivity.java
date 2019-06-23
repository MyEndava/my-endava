package com.endava.myendava.activities;

import android.os.Bundle;

import com.endava.myendava.R;
import com.endava.myendava.fragments.FilteredTagsFragment;

import androidx.appcompat.app.AppCompatActivity;

public class FilteredTagsActivity extends AppCompatActivity implements FilteredTagsFragment.OnFilteredTagsFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_tags);
    }

    @Override
    public void onTagAdded() {
        onBackPressed();
    }
}
