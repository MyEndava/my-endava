package com.endava.myendava.repositories;

import com.endava.myendava.models.AddTagRequest;
import com.endava.myendava.models.Tag;
import com.endava.myendava.rest.RetrofitClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class TagsRepository {

    private final RetrofitClient mRetrofitClient;

    public TagsRepository(RetrofitClient retrofitClient) {
        mRetrofitClient = retrofitClient;
    }

    public Observable<List<Tag>> getTags() {
        return mRetrofitClient.getRetrofitClient().getAllTags();
    }

    public Observable<List<Tag>> getFilteredTags(String email) {
        return mRetrofitClient.getRetrofitClient().getFilteredTags(email);
    }

    public Completable addTagToProfile(AddTagRequest request) {
        return mRetrofitClient.getRetrofitClient().addTagToProfile(request);
    }

}
