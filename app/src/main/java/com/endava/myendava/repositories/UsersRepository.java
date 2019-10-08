package com.endava.myendava.repositories;

import com.endava.myendava.models.LoginResult;
import com.endava.myendava.models.User;
import com.endava.myendava.rest.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;

public class UsersRepository {

    private final RetrofitClient mRetrofitClient;

    public UsersRepository(RetrofitClient retrofitClient) {
        mRetrofitClient = retrofitClient;
    }

    public Observable<List<User>> getUsersByTag(String tag) {
        return mRetrofitClient.getRetrofitClient().getUsersByTag(tag);
    }

    public Observable<LoginResult> isUserExistent(String email) {
        return mRetrofitClient.getRetrofitClient().userLoginMock(email);
    }
}
