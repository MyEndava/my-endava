package com.endava.myendava.presenters.fragments;

import android.util.Log;

import com.endava.myendava.Project;
import com.endava.myendava.presenters.BasePresenter;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.views.fragments.DashboardView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DashboardPresenter extends BasePresenter<DashboardView> {

    private MySharedPreferences mSharedPreferences;
    private CompositeDisposable compositeDisposable;
    private RetrofitClient retrofitClient;
    private List<Project> projects;

    public DashboardPresenter(MySharedPreferences mySharedPreferences) {
        mSharedPreferences = mySharedPreferences;
        retrofitClient = new RetrofitClient();
    }

    @Override
    public void viewReady() {
        projects = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(retrofitClient.getRetrofitClient().getAllProjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(projects -> {
                    Log.d("Projects", projects.toString());
                    this.projects.clear();
                    this.projects.addAll(projects);
                    mViewRef.get().showProjects(projects);
                }, throwable -> {
                    Log.e("Projects", throwable.getLocalizedMessage());
                    mViewRef.get().showMessage(throwable.getLocalizedMessage());
                }));
        mViewRef.get().setupAdapter(mSharedPreferences.isLoggedInAsGuest());
    }

    @Override
    public void viewGone() {
        compositeDisposable.clear();
    }

    public List<Project> getProjects() {
        return projects;
    }
}
