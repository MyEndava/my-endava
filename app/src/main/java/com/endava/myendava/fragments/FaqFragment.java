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
import com.endava.myendava.adapters.FaqRecyclerAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.presenters.fragments.BaseFragment;
import com.endava.myendava.presenters.fragments.FaqPresenter;
import com.endava.myendava.views.fragments.FaqView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FaqFragment extends BaseFragment implements FaqView, OnChipClickedListener {

    @BindView(R.id.faq_recycler_view)
    RecyclerView mFaqRecycler;

    @Inject
    FaqPresenter mPresenter;

    private FaqRecyclerAdapter mFaqAdapter;
    private Unbinder mUnbinder;
    private OnFaqFragmentInteractionListener mListener;

    public static FaqFragment newInstance() {
        return new FaqFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        informPresenterViewReady();
    }

    @Override
    public void setupRecyclerView(boolean isUserLoggedInAsGuest) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mFaqRecycler.setLayoutManager(layoutManager);
        mFaqRecycler.setItemAnimator(null);
        if(isUserLoggedInAsGuest){
            mFaqAdapter = new FaqRecyclerAdapter(mPresenter.getGuestsData(), this, true);
        }else{
            mFaqAdapter = new FaqRecyclerAdapter(mPresenter.getEmployeesData(), this, false);
        }
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
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFaqFragmentInteractionListener) {
            mListener = (OnFaqFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onChipClicked(Tag tag) {
        if (mListener != null) {
            mListener.onTagSelected(tag);
        }
    }

    public interface OnFaqFragmentInteractionListener {

        void onTagSelected(Tag tag);
    }
}
