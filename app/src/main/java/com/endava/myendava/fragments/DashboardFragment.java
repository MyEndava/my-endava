package com.endava.myendava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.presenters.fragments.BaseFragment;
import com.endava.myendava.presenters.fragments.DashboardPresenter;
import com.endava.myendava.views.fragments.DashboardView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DashboardFragment extends BaseFragment implements DashboardView {

    @Inject
    DashboardPresenter mPresenter;

    private Unbinder mUnbinder;

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
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getActivity().getApplicationContext();
        locator.getDashboardComponent(this).inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
