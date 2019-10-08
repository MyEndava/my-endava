package com.endava.myendava.di.activitiesDi;

import com.endava.myendava.activities.SignInActivity;
import com.endava.myendava.repositories.UsersRepository;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.viewmodels.UsersViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {

    private final SignInActivity mActivity;

    public SignInModule(SignInActivity activity) {
        mActivity = activity;
    }

    @Provides
    UsersViewModel provideUsersViewModel() {
        return new UsersViewModel(new UsersRepository(new RetrofitClient()));
    }
}
