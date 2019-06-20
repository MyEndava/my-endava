package com.endava.myendava.presenters.fragments;

import android.content.Context;
import android.widget.Toast;

import com.endava.myendava.models.ProjectModel;
import com.endava.myendava.presenters.BasePresenter;
import com.endava.myendava.utils.MockedDataGenerator;
import com.endava.myendava.views.fragments.DashboardView;

import java.util.List;

public class DashboardPresenter extends BasePresenter<DashboardView> {
    @Override
    public void viewReady() {

    }

    public List<ProjectModel> getItemsList(Context context) {
        MockedDataGenerator dataGenerator = new MockedDataGenerator();
        return dataGenerator.getMockedProjectsList(context);
    }

    public void onItemClick(Context context, ProjectModel projectModel) {
        Toast.makeText(context, projectModel.getName(), Toast.LENGTH_SHORT).show();
    }
}
