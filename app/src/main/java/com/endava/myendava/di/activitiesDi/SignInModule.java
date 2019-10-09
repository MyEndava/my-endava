package com.endava.myendava.di.activitiesDi;

import com.endava.myendava.activities.SignInActivity;
import com.endava.myendava.repositories.LoginRepository;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.viewmodels.LoginViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {

    private final SignInActivity mActivity;

    public SignInModule(SignInActivity activity) {
        mActivity = activity;
    }

    @Provides
    LoginViewModel provideLoginViewModel() {
        return new LoginViewModel(new LoginRepository(new RetrofitClient()));
    }
}
