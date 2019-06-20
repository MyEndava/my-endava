package com.endava.myendava.app;

import com.endava.myendava.activities.MainActivity;
import com.endava.myendava.activities.SignInActivity;
import com.endava.myendava.activities.SignInAsGuestActivity;
import com.endava.myendava.activities.SplashActivity;
import com.endava.myendava.di.FragmentsDi.DashboardComponent;
import com.endava.myendava.di.FragmentsDi.FaqComponent;
import com.endava.myendava.di.FragmentsDi.GuestInfoComponent;
import com.endava.myendava.di.FragmentsDi.HomeComponent;
import com.endava.myendava.di.FragmentsDi.NotificationsComponent;
import com.endava.myendava.di.activitiesDi.MainComponent;
import com.endava.myendava.di.activitiesDi.SignInAsGuestComponent;
import com.endava.myendava.di.activitiesDi.SignInComponent;
import com.endava.myendava.di.activitiesDi.SplashComponent;
import com.endava.myendava.fragments.DashboardFragment;
import com.endava.myendava.fragments.FaqFragment;
import com.endava.myendava.fragments.GuestInfoFragment;
import com.endava.myendava.fragments.HomeFragment;
import com.endava.myendava.fragments.NotificationsFragment;

public interface ApplicationServiceLocator {

    SplashComponent getSplashComponent(SplashActivity activity);

    SignInComponent getSignInComponent(SignInActivity activity);

    SignInAsGuestComponent getSignInAsGuestComponent(SignInAsGuestActivity activity);

    MainComponent getMainComponent(MainActivity activity);

    HomeComponent getHomeComponent(HomeFragment fragment);

    DashboardComponent getDashboardComponent(DashboardFragment fragment);

    NotificationsComponent getNotificationsComponent(NotificationsFragment fragment);

    FaqComponent getFaqComponent(FaqFragment fragment);

    GuestInfoComponent getGuestInfoComponent(GuestInfoFragment fragment);
}
