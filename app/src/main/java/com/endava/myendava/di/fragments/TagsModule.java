package com.endava.myendava.di.fragments;

import com.endava.myendava.fragments.FilteredTagsFragment;
import com.endava.myendava.fragments.SuggestDialogFragment;
import com.endava.myendava.fragments.TagsFragment;
import com.endava.myendava.repositories.TagsRepository;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.viewmodels.TagsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class TagsModule {

    private TagsFragment mTagsFragment;
    private FilteredTagsFragment mFilteredTagsFragment;
    private SuggestDialogFragment mSuggestTagsFragment;

    public TagsModule(SuggestDialogFragment fragment) {
        mSuggestTagsFragment = fragment;
    }

    public TagsModule(TagsFragment fragment) {
        mTagsFragment = fragment;
    }

    public TagsModule(FilteredTagsFragment fragment) {
        mFilteredTagsFragment = fragment;
    }

    @Provides
    TagsViewModel provideTagsViewModel() {
        return new TagsViewModel(new TagsRepository(new RetrofitClient()));
    }
}