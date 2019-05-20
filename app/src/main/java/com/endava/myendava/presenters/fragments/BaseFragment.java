package com.endava.myendava.presenters.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.endava.myendava.views.BaseView;

public abstract class BaseFragment extends Fragment implements BaseView {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return provideYourFragmentView(inflater, viewGroup, savedInstanceState);
    }

    public abstract View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

}
