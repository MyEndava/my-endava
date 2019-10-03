package com.endava.myendava.app;

import com.endava.myendava.activities.MainActivity;
import com.endava.myendava.activities.SignInActivity;
import com.endava.myendava.activities.SignInAsGuestActivity;
import com.endava.myendava.activities.SplashActivity;
import com.endava.myendava.activities.UsersActivity;
import com.endava.myendava.di.FragmentsDi.DashboardComponent;
import com.endava.myendava.di.FragmentsDi.FaqComponent;
import com.endava.myendava.di.FragmentsDi.GuestInfoComponent;
import com.endava.myendava.di.FragmentsDi.ProfileComponent;
import com.endava.myendava.di.FragmentsDi.TagsComponent;
import com.endava.myendava.di.activitiesDi.MainComponent;
import com.endava.myendava.di.activitiesDi.SignInAsGuestComponent;
import com.endava.myendava.di.activitiesDi.SignInComponent;
import com.endava.myendava.di.activitiesDi.SplashComponent;
import com.endava.myendava.di.activitiesDi.UsersComponent;
import com.endava.myendava.fragments.DashboardFragment;
import com.endava.myendava.fragments.FaqFragment;
import com.endava.myendava.fragments.FilteredTagsFragment;
import com.endava.myendava.fragments.GuestInfoFragment;
import com.endava.myendava.fragments.ProfileFragment;
import com.endava.myendava.fragments.TagsFragment;

public interface ApplicationServiceLocator {

    SplashComponent getSplashComponent(SplashActivity activity);

    SignInComponent getSignInComponent(SignInActivity activity);

    SignInAsGuestComponent getSignInAsGuestComponent(SignInAsGuestActivity activity);

    MainComponent getMainComponent(MainActivity activity);

    DashboardComponent getDashboardComponent(DashboardFragment fragment);

    FaqComponent getFaqComponent(FaqFragment fragment);

    GuestInfoComponent getGuestInfoComponent(GuestInfoFragment fragment);

    UsersComponent getUsersComponent(UsersActivity activity);

    ProfileComponent getProfileComponent(ProfileFragment fragment);

    TagsComponent getTagsComponent(TagsFragment fragment);

    TagsComponent getTagsComponent(FilteredTagsFragment fragment);
}
