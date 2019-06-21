package com.endava.myendava.rest;

import com.endava.myendava.Tag;
import com.endava.myendava.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitServiceApi {

    @GET("tag/getAll")
    Observable<List<Tag>> getAllTags();

    @GET("user/getByTag")
    Observable<List<User>> getUsersByTag(@Query("tag") String tag);
}
