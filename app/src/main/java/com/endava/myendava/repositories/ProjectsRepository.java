package com.endava.myendava.repositories;

import com.endava.myendava.models.Project;
import com.endava.myendava.rest.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;

public class ProjectsRepository {

    private final RetrofitClient mRetrofitClient;

    public ProjectsRepository(RetrofitClient retrofitClient) {
        this.mRetrofitClient = retrofitClient;
    }

    public Observable<List<Project>> getProjects() {
        return mRetrofitClient.getRetrofitClient().getAllProjects();
    }
}
