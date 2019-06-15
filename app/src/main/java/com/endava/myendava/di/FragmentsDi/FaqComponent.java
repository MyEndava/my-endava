package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.FaqFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {FaqModule.class})
public interface FaqComponent {

    void inject(FaqFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        FaqComponent.Builder faqBuilder(FaqModule module);

        FaqComponent build();
    }
}