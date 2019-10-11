package com.endava.myendava.fragments;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.endava.myendava.SuggestDialogFragment;
import com.endava.myendava.adapters.TagsAdapter;
import com.endava.myendava.models.Tag;
import com.endava.myendava.utils.KeyboardHelper;
import com.endava.myendava.models.TagCategory;
import com.endava.myendava.viewmodels.TagsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TagsFragment extends BaseFragment implements TagsAdapter.OnTagClickListener {

    @Inject
    TagsViewModel mTagsViewModel;
    @Inject
    KeyboardHelper mKeyboardHelper;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tags_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search)
    EditText mSearchBar;
    @BindView(R.id.multi_search)
    Button mMultiSearchButton;

    private TagsAdapter mAdapter;
    private OnTagsFragmentInteractionListener listener;

    private Unbinder mUnbinder;

    private boolean mIsMultiSearchClicked;

    private List<Tag> mSelectedTagsList;
    private List<Tag> mTagsList;

    public TagsFragment() {
        // required empty public constructor
    }

    public static TagsFragment newInstance() {
        return new TagsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor(R.color.secondary);
        setHasOptionsMenu(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tags;
    }

    @Override
    public void setupModule() {
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

        mTagsViewModel.getTags().observe(this, tags -> {
            mTagsList = tags;
            mAdapter.setData(tags);
        });

        mTagsViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(mProgressBar);
            } else {
                hideProgressBar(mProgressBar);
            }
        });
        mTagsViewModel.getError().observe(this, this::displayError);

        setupRecyclerView(view);
        mSearchBar.addTextChangedListener(getSearchWatcher());

        mSearchBar.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                mKeyboardHelper.hideSoftKeyboard(getActivity());
                return true;
            }
            return false;
        });
    }

    private TextWatcher getSearchWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<Tag> filteredList = new ArrayList<>();
                for (Tag tag : mTagsList) {
                    if (tag.getTagName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(tag);
                    }
                }
                mAdapter.setData(filteredList);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private void setupRecyclerView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TagsAdapter(getContext(), new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.multi_search)
    void multiSearchListener() {
        if (mMultiSearchButton.getText().toString().equalsIgnoreCase(getResources().getString(R.string.multiple_search))) {
            mMultiSearchButton.setText(getResources().getString(R.string.show_people));
            mMultiSearchButton.setEnabled(false);
            mIsMultiSearchClicked = true;
            mSelectedTagsList = new ArrayList<>();
        } else {
            if (mSelectedTagsList.size() > 0) {
                listener.onTagsSearch(mSelectedTagsList, R.id.navigation_tags);
            }
        }
    }

    @OnClick(R.id.suggest_tag_button)
    public void onSuggestTag() {
        mTagsViewModel.fetchTagCategories();
    }

    private void addSelectedTagsToList(Tag tag) {
        mMultiSearchButton.setEnabled(true);
        if (mSelectedTagsList.contains(tag)) {
            mSelectedTagsList.remove(tag);
            if (mSelectedTagsList.size() == 0) {
                mMultiSearchButton.setText(getResources().getString(R.string.multiple_search));
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
                List<Tag> tagList = new ArrayList<>();
                tagList.add(tag);
                listener.onTagsSearch(tagList, R.id.navigation_tags);
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

        void onAddSkillClicked();

        void onTagsSearch(List<Tag> selectedTagsList, int navigationId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mTagsViewModel.onCleared();
    }
}
