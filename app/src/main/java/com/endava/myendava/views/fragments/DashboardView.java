package com.endava.myendava.views.fragments;

import com.endava.myendava.Project;

import java.util.List;

public interface DashboardView {

    void setupAdapter(boolean isUserLoggedInAsGuest);

    void showProjects(List<Project> projects);

    void showMessage(String message);
}
