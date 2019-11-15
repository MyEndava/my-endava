package com.endava.myendava.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.FaqsAdapter;
import com.endava.myendava.listeners.OnChipClickedListener;
import com.endava.myendava.models.Faq;
import com.endava.myendava.models.Tag;
import com.endava.myendava.utils.KeyboardHelper;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.viewmodels.FaqsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FaqFragment extends BaseFragment implements OnChipClickedListener {

    @Inject
    MySharedPreferences mSharedPreferences;
    @Inject
    FaqsViewModel mFaqViewModel;
    @Inject
    KeyboardHelper mKeyboardHelper;

    @BindView(R.id.faq_recycler_view)
    RecyclerView mFaqRecycler;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.search)
    EditText mSearchBar;

    private FaqsAdapter mAdapter;
    private Unbinder mUnbinder;
    private OnFaqFragmentInteractionListener mListener;
    private List<Faq> mFaqList;


    public static FaqFragment newInstance() {
        return new FaqFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);

        mFaqViewModel.getFaqs().observe(this, faqs -> {
                    mFaqList = faqs;
                    mAdapter.setData(faqs);
                }
        );

        mFaqViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(mProgressBar);
            } else {
                hideProgressBar(mProgressBar);
            }
        });
        mFaqViewModel.getError().observe(this, this::displayError);

        setupRecyclerView();
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
                List<Faq> filteredList = new ArrayList<>();
                for (Faq faq : mFaqList) {
                    if (faq.getQuestion().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(faq);
                    }
                }
                mAdapter.setData(filteredList);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_faq;
    }

    @Override
    public void setupModule() {
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
            List<Tag> tagList = new ArrayList<>();
            tagList.add(tag);
            mListener.onTagsSearch(tagList, R.id.navigation_faq);
        }
    }

    public interface OnFaqFragmentInteractionListener {

        void onTagsSearch(List<Tag> tagList, int navigationId);
    }
}
