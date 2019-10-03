package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.ProfileFragment;
import com.endava.myendava.repositories.ProfileRepository;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.viewmodels.ProfileViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {

    private final ProfileFragment mFragment;

    public ProfileModule(ProfileFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    ProfileViewModel provideProfileViewModel() {
        return new ProfileViewModel(new ProfileRepository(new RetrofitClient()));
    }
}