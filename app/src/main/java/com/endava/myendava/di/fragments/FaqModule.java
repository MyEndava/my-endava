package com.endava.myendava.di.fragments;

import com.endava.myendava.fragments.FaqFragment;
import com.endava.myendava.repositories.FaqRepository;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.viewmodels.FaqsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FaqModule {

    private final FaqFragment mFragment;

    public FaqModule(FaqFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    FaqsViewModel provideFaqsViewModel() {
        return new FaqsViewModel(new FaqRepository(new RetrofitClient()));
    }
}