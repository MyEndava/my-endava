package com.endava.myendava.rest;

import com.endava.myendava.models.AddTagRequest;
import com.endava.myendava.models.Faq;
import com.endava.myendava.models.LoginResult;
import com.endava.myendava.models.Profile;
import com.endava.myendava.models.Project;
import com.endava.myendava.models.Tag;
import com.endava.myendava.models.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitServiceApi {

    @GET("user/login/{email}")
    Observable<LoginResult> userLoginEmailCheck(@Path("email") String email);

    @GET("tag/getAll")
    Observable<List<Tag>> getAllTags();

    @GET("user/getByTags/{tag}")
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

    @GET("faq/getAll")
    Observable<List<Faq>> getAllFaqs();
}
