package com.endava.myendava.app;

import com.endava.myendava.activities.MainActivity;
import com.endava.myendava.activities.SignInActivity;
import com.endava.myendava.activities.SignInAsGuestActivity;
import com.endava.myendava.activities.SplashActivity;
import com.endava.myendava.activities.UsersActivity;
import com.endava.myendava.di.fragments.DashboardComponent;
import com.endava.myendava.di.fragments.FaqComponent;
import com.endava.myendava.di.fragments.GuestInfoComponent;
import com.endava.myendava.di.fragments.ProfileComponent;
import com.endava.myendava.di.fragments.TagsComponent;
import com.endava.myendava.di.activities.MainComponent;
import com.endava.myendava.di.activities.SignInAsGuestComponent;
import com.endava.myendava.di.activities.SignInComponent;
import com.endava.myendava.di.activities.SplashComponent;
import com.endava.myendava.di.activities.UsersComponent;
import com.endava.myendava.fragments.CalendarFragment;
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

    CalendarComponent getDashboardComponent(CalendarFragment fragment);

    FaqComponent getFaqComponent(FaqFragment fragment);

    GuestInfoComponent getGuestInfoComponent(GuestInfoFragment fragment);

    UsersComponent getUsersComponent(UsersActivity activity);

    ProfileComponent getProfileComponent(ProfileFragment fragment);

    TagsComponent getTagsComponent(TagsFragment fragment);

    TagsComponent getTagsComponent(FilteredTagsFragment fragment);
}
