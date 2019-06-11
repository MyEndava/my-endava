package com.endava.myendava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.FaqRecyclerAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.presenters.fragments.BaseFragment;
import com.endava.myendava.presenters.fragments.FaqPresenter;
import com.endava.myendava.views.fragments.FaqView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FaqFragment extends BaseFragment implements FaqView {

    @Inject
    FaqPresenter mPresenter;

    private Unbinder mUnbinder;

    @BindView(R.id.faq_recycler_view)
    RecyclerView mFaqRecycler;

    FaqRecyclerAdapter mFaqAdapter;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
        informPresenterViewReady();
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mFaqRecycler.setLayoutManager(layoutManager);
        mFaqAdapter = new FaqRecyclerAdapter(mPresenter.getMockData());
        mFaqRecycler.setAdapter(mFaqAdapter);
    }

    private void informPresenterViewReady() {
        mPresenter.viewReady();
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getActivity().getApplicationContext();
        locator.getFaqComponent(this).inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
