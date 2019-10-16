package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.CalendarFragment;
import com.endava.myendava.repositories.ProjectsRepository;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.viewmodels.ProjectsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class CalendarModule {

    private final CalendarFragment mFragment;

    public CalendarModule(CalendarFragment fragment) {
        mFragment = fragment;
    }

}