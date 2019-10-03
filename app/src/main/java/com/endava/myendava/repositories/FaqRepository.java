package com.endava.myendava.repositories;

import com.endava.myendava.models.Faq;
import com.endava.myendava.rest.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;

public class FaqRepository {

    private final RetrofitClient mRetrofitClient;

    public FaqRepository(RetrofitClient retrofitClient) {
        mRetrofitClient = retrofitClient;
    }

    public Observable<List<Faq>> getFaqs() {
        return mRetrofitClient.getRetrofitClient().getAllFaqs();
    }
}
