package com.endava.myendava.di.activitiesDi;

import com.endava.myendava.activities.SignInActivity;
import com.endava.myendava.repositories.SignInRepository;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.viewmodels.SignInViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {

    private final SignInActivity mActivity;

    public SignInModule(SignInActivity activity) {
        mActivity = activity;
    }

    @Provides
    SignInViewModel provideSignInViewModel() {
        return new SignInViewModel(new SignInRepository(new RetrofitClient()));
    }
}
