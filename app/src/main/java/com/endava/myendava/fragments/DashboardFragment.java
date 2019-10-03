package com.endava.myendava.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.ProjectsAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.listeners.OnChipClickedListener;
import com.endava.myendava.models.Tag;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.viewmodels.ProjectsViewModel;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DashboardFragment extends BaseFragment implements OnChipClickedListener {

    @Inject
    ProjectsViewModel mProjectsViewModel;

    @Inject
    MySharedPreferences mSharedPreferences;

    @BindView(R.id.projects_list)
    RecyclerView mItemsRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private ProjectsAdapter mAdapter;
    private Unbinder mUnbinder;
    private OnDashboardFragmentInteractionListener mListener;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View provideFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);

        mProjectsViewModel.getProjects().observe(this, projects -> mAdapter.setData(projects));

        mProjectsViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(mProgressBar);
            } else {
                hideProgressBar(mProgressBar);
            }
        });
        mProjectsViewModel.getError().observe(this, this::displayError);

        setupAdapter();
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) Objects.requireNonNull(getActivity()).getApplicationContext();
        locator.getDashboardComponent(this).inject(this);
    }

    private void setupAdapter() {
        mAdapter = new ProjectsAdapter(getContext(), new ArrayList<>(),
                this, mSharedPreferences.isLoggedInAsGuest());
        mItemsRecyclerView.setAdapter(mAdapter);
        mItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setOnMyItemClickListener(this::onProjectClicked);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDashboardFragmentInteractionListener) {
            mListener = (OnDashboardFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    private void onProjectClicked(String projectName) {
        if (mListener != null) {
            mListener.onSkillSelected(new Tag("Project", "Project", null,
                    -1, projectName, ""), R.id.navigation_dashboard);
        }
    }

    @Override
    public void onChipClicked(Tag tag) {
        if (mListener != null) {
            mListener.onSkillSelected(tag, R.id.navigation_dashboard);
        }
    }

    public interface OnDashboardFragmentInteractionListener {

        void onSkillSelected(Tag tag, int navigationId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.setOnMyItemClickListener(null);
        mUnbinder.unbind();
        mProjectsViewModel.onCleared();
    }

}
