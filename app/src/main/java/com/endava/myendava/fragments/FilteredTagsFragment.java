package com.endava.myendava.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.endava.myendava.models.AddTagRequest;
import com.endava.myendava.models.Tag;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.rest.RetrofitServiceApi;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.viewmodels.TagsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FilteredTagsFragment extends BaseFragment implements TagsAdapter.OnTagClickListener {

    @Inject
    MySharedPreferences mSharedPreferences;
    @Inject
    TagsViewModel mTagsViewModel;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private Unbinder mUnbinder;

    private List<Tag> tags = new ArrayList<>();

    private TagsAdapter mAdapter;

    private OnFilteredTagsFragmentInteractionListener listener;

    public FilteredTagsFragment() {
    }

    @Override
    public View provideFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtered_tags, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);

        mTagsViewModel.getFilteredTags(mSharedPreferences.getUserEmail()).observe(this, faqs -> mAdapter.setData(faqs));

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
        mAdapter = new TagsAdapter(getContext(), tags, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getActivity().getApplicationContext();
        locator.getTagsComponent(this).inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilteredTagsFragment.OnFilteredTagsFragmentInteractionListener) {
            listener = (FilteredTagsFragment.OnFilteredTagsFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onTagClicked(Tag tag) {
        mTagsViewModel.addTag(new AddTagRequest(tag.getTagId(),
                1, mSharedPreferences.getUserEmail())).observe(this, aBoolean -> listener.onTagAdded());
    }

    @Override
    public List<Tag> getSelectedTags() {
        return null;
    }

    @Override
    public boolean isMultiSearchClicked() {
        return false;
    }

    public interface OnFilteredTagsFragmentInteractionListener {

        void onTagAdded();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mTagsViewModel.onCleared();
    }
}
