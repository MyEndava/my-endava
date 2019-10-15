package com.endava.myendava.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.UsersAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.models.Tag;
import com.endava.myendava.models.User;
import com.endava.myendava.viewmodels.UsersViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.endava.myendava.adapters.TagsAdapter.CATEGORY_LOGISTICS;
import static com.endava.myendava.adapters.TagsAdapter.CATEGORY_PROJECT;
import static com.endava.myendava.adapters.TagsAdapter.CATEGORY_SOFT;
import static com.endava.myendava.adapters.TagsAdapter.CATEGORY_TECHNICAL;

public class UsersActivity extends AppCompatActivity implements UsersAdapter.OnUserClickListener {

    @Inject
    UsersViewModel mUsersViewModel;

    public static final String ARG_TAG = "arg_tag_name";

    @BindView(R.id.tag_description_text_view)
    TextView tagDescription;
    @BindView(R.id.tag_title)
    TextView tagTitle;
    @BindView(R.id.tag_type)
    TextView tagType;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.arrow_back_button)
    ImageView backPressImageViewButton;

    private Tag tag;
    private List<User> users = new ArrayList<>();
    private UsersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);
        setupModule();
        setupRecyclerView();
        populateSelectedTag();
    }

    public void setIconToTagType(){
        Drawable icon = null;
        switch (tag.getSubcategory()) {
            case CATEGORY_TECHNICAL:
                icon = getResources().getDrawable(R.drawable.icons_filled_settings);
                break;
            case CATEGORY_SOFT:
                icon = getResources().getDrawable(R.drawable.ic_icons_filled_comment_white);
                break;
            case CATEGORY_PROJECT:
                icon = getResources().getDrawable(R.drawable.icons_filled_tags);
                break;
            case CATEGORY_LOGISTICS:
                icon = getResources().getDrawable(R.drawable.ic_logistics);
                break;
            default:
                break;
        }
        tagType.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
    }

    private void populateSelectedTag() {
        tag = (Tag) getIntent().getSerializableExtra(ARG_TAG);
        tagTitle.setText(tag.getTagName());
        backPressImageViewButton.setOnClickListener(view -> onBackPressed());
        tagDescription.setText(tag.getTagDescription());
        tagType.setText(tag.getCategory());
        setIconToTagType();
        setFieldsColor(getResources().getColor(R.color.white));
    }

    private void setFieldsColor(int color) {
        tagTitle.setTextColor(color);
        tagDescription.setTextColor(color);
        tagType.setTextColor(color);
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