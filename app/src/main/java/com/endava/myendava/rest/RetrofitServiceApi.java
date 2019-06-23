package com.endava.myendava.rest;

import com.endava.myendava.AddTagRequest;
import com.endava.myendava.Profile;
import com.endava.myendava.Project;
import com.endava.myendava.Tag;
import com.endava.myendava.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitServiceApi {

    @GET("tag/getAll")
    Observable<List<Tag>> getAllTags();

    @GET("user/getByTag/{tag}")
    Observable<List<User>> getUsersByTag(@Path("tag") String tag);

    @GET("project/getAll")
    Observable<List<Project>> getAllProjects();

    @GET("profile/{email}")
    Observable<Profile> getProfile(@Path("email") String email);

    @GET("user/tags/new/{email}")
    Observable<List<Tag>> getFilteredTags(@Path("email") String userEmail);

    @POST("user/addTag")
    @Headers("Content-Type: application/json")
    Completable addTagToProfile(@Body AddTagRequest request);
}
