package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.DashboardFragment;
import com.endava.myendava.repositories.ProjectsRepository;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.viewmodels.ProjectsViewModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class DashboardModule {

    private final DashboardFragment mFragment;

    public DashboardModule(DashboardFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    ProjectsViewModel provideProjectsViewModel() {
        return new ProjectsViewModel(new ProjectsRepository(new RetrofitClient()));
    }
}