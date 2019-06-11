package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.FaqFragment;
import com.endava.myendava.presenters.fragments.FaqPresenter;
import com.endava.myendava.views.fragments.FaqView;

import dagger.Module;
import dagger.Provides;

@Module
public class FaqModule {

    private final FaqFragment mFragment;

    public FaqModule(FaqFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    FaqPresenter provideFaqPresenter() {
        FaqPresenter presenter = new FaqPresenter();
        presenter.setView((FaqView) mFragment);
        return presenter;
    }
}