package com.endava.myendava.repositories;

import com.endava.myendava.models.Profile;
import com.endava.myendava.models.UpdateProfileRequest;
import com.endava.myendava.rest.RetrofitClient;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class ProfileRepository {

    private final RetrofitClient mRetrofitClient;

    public ProfileRepository(RetrofitClient retrofitClient) {
        mRetrofitClient = retrofitClient;
    }

    public Observable<Profile> getProfile(String email) {
        return mRetrofitClient.getRetrofitClient().getProfile(email);
    }

    public Completable updateProfile(UpdateProfileRequest request) {
        return mRetrofitClient.getRetrofitClient().updateProfile(request);
    }
}
