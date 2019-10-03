package com.endava.myendava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.UsersAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.models.Tag;
import com.endava.myendava.models.User;
import com.endava.myendava.viewmodels.UsersViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersActivity extends AppCompatActivity implements UsersAdapter.OnUserClickListener {

    @Inject
    UsersViewModel mUsersViewModel;

    public static final String ARG_TAG = "arg_tag_name";

    @BindView(R.id.tag_description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private Tag tag;
    private List<User> users = new ArrayList<>();
    private UsersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);
        setupModule();
        setupActionBar();
        setupRecyclerView();
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tag = (Tag) getIntent().getSerializableExtra(ARG_TAG);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(tag.getTagName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        descriptionTextView.setText(tag.getTagDescription());
        collapsingToolbarLayout.post(() -> collapsingToolbarLayout.setScrimVisibleHeightTrigger(collapsingToolbarLayout.getMeasuredHeight() - 150));
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.people_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new UsersAdapter(this, users, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getApplication();
        locator.getUsersComponent(this).inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mUsersViewModel.getUsersByTag(tag.getTagName()).observe(this, users -> mAdapter.setData(users));

        mUsersViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar();
            } else {
                hideProgressbar();
            }
        });
        mUsersViewModel.getError().observe(this, this::displayError);
    }

    private void displayError(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(UsersActivity.this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.ARG_EMAIL, user.getEmail());
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(UsersActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUsersViewModel.onCleared();
    }
}
