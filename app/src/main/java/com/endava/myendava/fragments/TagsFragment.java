package com.endava.myendava.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.TagsAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.models.Tag;
import com.endava.myendava.viewmodels.TagsViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TagsFragment extends BaseFragment implements TagsAdapter.OnTagClickListener {

    @Inject
    TagsViewModel mTagsViewModel;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private TagsAdapter mAdapter;
    private OnTagsFragmentInteractionListener listener;
    private Unbinder mUnbinder;

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

        mTagsViewModel.getTags().observe(this, faqs -> mAdapter.setData(faqs));

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
        RecyclerView recyclerView = view.findViewById(R.id.tags_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TagsAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(mAdapter);
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
            listener.onSkillSelected(tag, R.id.navigation_tags);
        }
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
