package com.endava.myendava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return provideFragmentView(inflater, viewGroup, savedInstanceState);
    }

    public abstract View provideFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    void displayError(String error){
        if(error != null){
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }

    void showProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.GONE);
    }
}
