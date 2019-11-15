package com.endava.myendava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.endava.myendava.app.ApplicationServiceLocator;


public abstract class BaseFragment extends Fragment {

    protected ApplicationServiceLocator locator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return provideFragmentView(inflater, viewGroup);
    }

    private View provideFragmentView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(getResources().getLayout(getLayoutId()), parent, false);
        locator = (ApplicationServiceLocator) getActivity().getApplicationContext();
        setupModule();
        return view;
    }

    public abstract int getLayoutId();

    public abstract void setupModule();

    void displayError(String error) {
        if (error != null) {
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }

    void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }
}
