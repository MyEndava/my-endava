package com.endava.myendava.presenters.fragments;

import android.content.Context;
import android.widget.Toast;

import com.endava.myendava.models.ProjectModel;
import com.endava.myendava.presenters.BasePresenter;
import com.endava.myendava.utils.MockedProjectsGenerator;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.views.fragments.DashboardView;

import java.util.List;

public class DashboardPresenter extends BasePresenter<DashboardView> {

    private MySharedPreferences mSharedPreferences;

    public DashboardPresenter(MySharedPreferences mySharedPreferences) {
        mSharedPreferences = mySharedPreferences;
    }

    @Override
    public void viewReady() {
        mViewRef.get().setupAdapter(mSharedPreferences.isLoggedInAsGuest());
    }

    public List<ProjectModel> getItemsList(Context context) {
        MockedProjectsGenerator dataGenerator = new MockedProjectsGenerator();
        return dataGenerator.getMockedProjectsList(context);
    }
}
