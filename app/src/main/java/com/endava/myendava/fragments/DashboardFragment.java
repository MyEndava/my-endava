package com.endava.myendava.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.OnChipClickedListener;
import com.endava.myendava.R;
import com.endava.myendava.Tag;
import com.endava.myendava.TagGroup;
import com.endava.myendava.adapters.ProjectsAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.models.ProjectModel;
import com.endava.myendava.presenters.fragments.BaseFragment;
import com.endava.myendava.presenters.fragments.DashboardPresenter;
import com.endava.myendava.views.fragments.DashboardView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DashboardFragment extends BaseFragment implements DashboardView, OnChipClickedListener {

    @Inject
    DashboardPresenter mPresenter;

    @BindView(R.id.projects_list)
    RecyclerView mItemsRecyclerView;

    private ProjectsAdapter mAdapter;
    private Unbinder mUnbinder;
    private OnDashboardFragmentInteractionListener mListener;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        informPresenterViewReady();
    }

    private void informPresenterViewReady() {
        mPresenter.viewReady();
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) Objects.requireNonNull(getActivity()).getApplicationContext();
        locator.getDashboardComponent(this).inject(this);
    }

    @Override
    public void setupAdapter(boolean isUserLoggedInAsGuest) {
        mAdapter = new ProjectsAdapter(getContext(), this, isUserLoggedInAsGuest);
        mItemsRecyclerView.setAdapter(mAdapter);
        mItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<ProjectModel> mItemsList = mPresenter.getItemsList(getContext());
        mAdapter.setData(mItemsList);
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
        if(mListener != null) {
            mListener.onSkillSelected(new Tag(projectName, new TagGroup("Project", null)));
        }
    }

    @Override
    public void onChipClicked(Tag tag) {
        if (mListener != null) {
            mListener.onSkillSelected(tag);
        }
    }

    public interface OnDashboardFragmentInteractionListener {
        void onSkillSelected(Tag tag);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.setOnMyItemClickListener(null);
        mUnbinder.unbind();
    }

}
