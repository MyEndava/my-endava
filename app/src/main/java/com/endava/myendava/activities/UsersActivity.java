package com.endava.myendava.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
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

public class UsersActivity extends BaseFullScreenActivity implements UsersAdapter.OnUserClickListener {

    @Inject
    UsersViewModel usersViewModel;

    public static final String ARG_TAG_LIST = "arg_tag_list";

    @BindView(R.id.tag_description_text_view)
    TextView tagDescription;
    @BindView(R.id.tag_title)
    TextView tagTitle;
    @BindView(R.id.tag_type)
    TextView tagType;
    @BindView(R.id.arrow_back_button)
    ImageView backImageView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.my_container)
    ConstraintLayout container;
    @BindView(R.id.bottom_view)
    View bottomView;


    private List<Tag> tagList;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);
        setupModule();
        setFullScreen();
        setNavigationBar();
        initData();
        setupRecyclerView();
    }

    private void setIconToTagType(Tag tag) {
        tagType.setVisibility(View.VISIBLE);
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

    private void initData() {
        if (getIntent().getSerializableExtra(ARG_TAG_LIST) != null) {
            tagList = (List<Tag>) getIntent().getSerializableExtra(ARG_TAG_LIST);
        }
    }

    private void populateViews(Tag tag) {
        tagTitle.setText(tag.getTagName());
        tagDescription.setText(tag.getTagDescription());
        setIconToTagType(tag);
    }

    private void populateViews(List<Tag> tagsList, int usersListSize) {
        String iteratedTagsList = usersViewModel.mapTagsList(tagsList).replace(",", ", ");
        tagTitle.setText(iteratedTagsList);
        tagDescription.setText(getDescriptionForTagsList(usersListSize).concat(iteratedTagsList));
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.people_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        usersAdapter = new UsersAdapter(this, new ArrayList<>(), this);
        recyclerView.setAdapter(usersAdapter);
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getApplication();
        locator.getUsersComponent(this).inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        backImageView.setOnClickListener(view -> onBackPressed());

        usersViewModel.getUsersByTagList(tagList).observe(this, users -> {
            usersAdapter.setData(users);
            if (tagList.size() == 1) {
                populateViews(tagList.get(0));
            } else {
                populateViews(tagList, users.size());
            }
        });

        usersViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(progressBar);
            } else {
                hideProgressBar(progressBar);
            }
        });
        usersViewModel.getError().observe(this, this::displayError);
    }

    private String getDescriptionForTagsList(int usersListSize) {
        String description;
        if (usersListSize == 0) {
            description = getResources().getString(R.string.no_users_found);
        } else {
            description = getResources().getString(R.string.multiple_search_description);
        }
        return description;
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
        usersViewModel.onCleared();
    }

    @Override
    protected ConstraintLayout getContainer() {
        return container;
    }

    @Override
    protected int getBottomViewId() {
        return bottomView.getId();
    }
}