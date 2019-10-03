package com.endava.myendava.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.FaqsAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.listeners.OnChipClickedListener;
import com.endava.myendava.models.Tag;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.viewmodels.FaqsViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FaqFragment extends BaseFragment implements OnChipClickedListener {

    @Inject
    MySharedPreferences mSharedPreferences;

    @Inject
    FaqsViewModel mFaqViewModel;

    @BindView(R.id.faq_recycler_view)
    RecyclerView mFaqRecycler;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private FaqsAdapter mAdapter;
    private Unbinder mUnbinder;
    private OnFaqFragmentInteractionListener mListener;

    public static FaqFragment newInstance() {
        return new FaqFragment();
    }

    @Override
    public View provideFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);

        mFaqViewModel.getFaqs().observe(this, faqs -> mAdapter.setData(faqs));

        mFaqViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(mProgressBar);
            } else {
                hideProgressBar(mProgressBar);
            }
        });
        mFaqViewModel.getError().observe(this, this::displayError);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mFaqRecycler.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mFaqRecycler.getContext(),
                DividerItemDecoration.VERTICAL);
        mFaqRecycler.setHasFixedSize(true);
        mFaqRecycler.addItemDecoration(dividerItemDecoration);
        mFaqRecycler.setItemAnimator(null);
        mAdapter = new FaqsAdapter(new ArrayList<>(),
                this, mSharedPreferences.isLoggedInAsGuest());
        mFaqRecycler.setAdapter(mAdapter);
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
        mFaqViewModel.onCleared();
    }

    @Override
    public void onChipClicked(Tag tag) {
        if (mListener != null) {
            mListener.onSkillSelected(tag, R.id.navigation_faq);
        }
    }

    public interface OnFaqFragmentInteractionListener {

        void onSkillSelected(Tag tag, int navigationId);
    }
}
