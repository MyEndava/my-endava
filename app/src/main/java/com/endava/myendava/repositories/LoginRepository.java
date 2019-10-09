package com.endava.myendava.repositories;

import com.endava.myendava.models.LoginResult;
import com.endava.myendava.rest.RetrofitClient;

import io.reactivex.Observable;

public class LoginRepository {
    private final RetrofitClient mRetrofitClient;

    public LoginRepository(RetrofitClient mRetrofitClient) {
        this.mRetrofitClient = mRetrofitClient;
    }

    public Observable<LoginResult> login(String email) {
        return mRetrofitClient.getRetrofitClient().login(email);
    }
}
