package com.endava.myendava.repositories;

import com.endava.myendava.models.LoginResult;
import com.endava.myendava.rest.RetrofitClient;

import io.reactivex.Observable;

public class SignInRepository {
    private final RetrofitClient mRetrofitClient;

    public SignInRepository(RetrofitClient mRetrofitClient) {
        this.mRetrofitClient = mRetrofitClient;
    }

    public Observable<LoginResult> isUserExistent(String email) {
        return mRetrofitClient.getRetrofitClient().userLoginEmailCheck(email);
    }
}
