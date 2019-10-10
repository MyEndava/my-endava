package com.endava.myendava.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.TagsAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.models.Tag;
import com.endava.myendava.viewmodels.TagsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TagsFragment extends BaseFragment implements TagsAdapter.OnTagClickListener {

    public static final String MULTIPLE_SEARCH = "MULTIPLE SEARCH";
    public static final String SHOW_PEOPLE = "SHOW PEOPLE";

    @Inject
    TagsViewModel mTagsViewModel;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tags_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search)
    EditText mSearchEditText;
    @BindView(R.id.multi_search)
    Button mMultiSearchButton;

    private TagsAdapter mAdapter;

    private OnTagsFragmentInteractionListener listener;

    private Unbinder mUnbinder;

    private boolean mIsMultiSearchClicked;

    private List<Tag> mSelectedTagsList;

    public TagsFragment() {
        // required empty public constructor
    }

    public static TagsFragment newInstance() {
        return new TagsFragment();
    }

    @Override
    public View provideFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tags, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor(getActivity().getResources().getColor(R.color.secondary));
        setHasOptionsMenu(true);
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getActivity().getApplicationContext();
        locator.getTagsComponent(this).inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnTagsFragmentInteractionListener) {
            listener = (OnTagsFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);

        mTagsViewModel.getTags().observe(this, tags -> mAdapter.setData(tags));

        mTagsViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(mProgressBar);
            } else {
                hideProgressBar(mProgressBar);
            }
        });
        mTagsViewModel.getError().observe(this, this::displayError);

        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mRecyclerView = view.findViewById(R.id.tags_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TagsAdapter(getContext(), new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.multi_search)
    public void multiSearchListener() {
        if (mMultiSearchButton.getText().toString().equalsIgnoreCase(MULTIPLE_SEARCH)) {
            mMultiSearchButton.setText(SHOW_PEOPLE);
            mMultiSearchButton.setEnabled(false);
            mIsMultiSearchClicked = true;
            mSelectedTagsList = new ArrayList<>();
        } else {
            if (mSelectedTagsList.size() > 0) {
                Toast.makeText(getContext(), "LIST SIZE " + mSelectedTagsList.size(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addSelectedTagsToList(Tag tag) {
        mMultiSearchButton.setEnabled(true);
        if (mSelectedTagsList.contains(tag)) {
            mSelectedTagsList.remove(tag);
            if (mSelectedTagsList.size() == 0) {
                mMultiSearchButton.setText(MULTIPLE_SEARCH);
                mIsMultiSearchClicked = false;
            }
        } else {
            mSelectedTagsList.add(tag);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_profile, menu);
        MenuItem item = menu.findItem(R.id.search_item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_tag_item:
                if (listener != null) {
                    listener.onAddSkillClicked();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTagClicked(Tag tag) {
        if (listener != null) {
            if (mIsMultiSearchClicked) {
                addSelectedTagsToList(tag);
            } else {
                listener.onSkillSelected(tag, R.id.navigation_tags);
            }
        }
    }

    @Override
    public List<Tag> getSelectedTags() {
        return mSelectedTagsList;
    }

    @Override
    public boolean isMultiSearchClicked() {
        return mIsMultiSearchClicked;
    }

    public interface OnTagsFragmentInteractionListener {

        void onSkillSelected(Tag tag, int navigationId);

        void onAddSkillClicked();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mTagsViewModel.onCleared();
    }
}
