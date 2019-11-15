package com.endava.myendava.app;

import android.app.Application;

import com.endava.myendava.activities.MainActivity;
import com.endava.myendava.activities.SignInActivity;
import com.endava.myendava.activities.SignInAsGuestActivity;
import com.endava.myendava.activities.SplashActivity;
import com.endava.myendava.activities.UsersActivity;
import com.endava.myendava.di.DaggerMyEndavaApplicationComponent;
import com.endava.myendava.di.MyEndavaApplicationModule;
import com.endava.myendava.di.activities.MainComponent;
import com.endava.myendava.di.activities.MainModule;
import com.endava.myendava.di.activities.SignInAsGuestComponent;
import com.endava.myendava.di.activities.SignInAsGuestModule;
import com.endava.myendava.di.activities.SignInComponent;
import com.endava.myendava.di.activities.SignInModule;
import com.endava.myendava.di.activities.SplashComponent;
import com.endava.myendava.di.activities.SplashModule;
import com.endava.myendava.di.activities.UsersComponent;
import com.endava.myendava.di.activities.UsersModule;
import com.endava.myendava.di.fragments.CalendarComponent;
import com.endava.myendava.di.fragments.CalendarModule;
import com.endava.myendava.di.fragments.FaqComponent;
import com.endava.myendava.di.fragments.FaqModule;
import com.endava.myendava.di.fragments.GuestInfoComponent;
import com.endava.myendava.di.fragments.GuestInfoModule;
import com.endava.myendava.di.fragments.ProfileComponent;
import com.endava.myendava.di.fragments.ProfileModule;
import com.endava.myendava.di.fragments.TagsComponent;
import com.endava.myendava.di.fragments.TagsModule;
import com.endava.myendava.fragments.CalendarFragment;
import com.endava.myendava.fragments.FaqFragment;
import com.endava.myendava.fragments.GuestInfoFragment;
import com.endava.myendava.fragments.ProfileFragment;
import com.endava.myendava.fragments.TagsFragment;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;
import javax.inject.Provider;

public class MyEndavaApplication extends Application implements ApplicationServiceLocator {

    @Inject
    Provider<SplashComponent.Builder> mSplashComponentProvider;

    @Inject
    Provider<SignInComponent.Builder> mSignInComponentProvider;

    @Inject
    Provider<SignInAsGuestComponent.Builder> mSignInAsGuestComponentProvider;

    @Inject
    Provider<MainComponent.Builder> mMainComponentProvider;

    @Inject
    Provider<CalendarComponent.Builder> mDashboardComponentProvider;

    @Inject
    Provider<FaqComponent.Builder> mFaqComponentProvider;

    @Inject
    Provider<GuestInfoComponent.Builder> mGuestInfoComponentProvider;

    @Inject
    Provider<UsersComponent.Builder> mUsersComponentProvider;

    @Inject
    Provider<ProfileComponent.Builder> mProfileComponentProvider;

    @Inject
    Provider<TagsComponent.Builder> mTagsComponentProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDagger();
        Stetho.initializeWithDefaults(this);
    }

    private void setupDagger() {
        DaggerMyEndavaApplicationComponent.builder()
                .myEndavaAppModule(new MyEndavaApplicationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public SplashComponent getSplashComponent(SplashActivity activity) {
        return mSplashComponentProvider.get()
                .splashBuilder(new SplashModule(activity))
                .build();
    }

    @Override
    public SignInComponent getSignInComponent(SignInActivity activity) {
        return mSignInComponentProvider.get()
                .signInBuilder(new SignInModule(activity))
                .build();
    }

    @Override
    public SignInAsGuestComponent getSignInAsGuestComponent(SignInAsGuestActivity activity) {
        return mSignInAsGuestComponentProvider.get()
                .signInAsGuestBuilder(new SignInAsGuestModule(activity))
                .build();
    }

    @Override
    public MainComponent getMainComponent(MainActivity activity) {
        return mMainComponentProvider.get()
                .mainBuilder(new MainModule(activity))
                .build();
    }

    @Override
    public CalendarComponent getDashboardComponent(CalendarFragment fragment) {
        return mDashboardComponentProvider.get()
                .calendarBuilder(new CalendarModule(fragment))
                .build();
    }

    @Override
    public FaqComponent getFaqComponent(FaqFragment fragment) {
        return mFaqComponentProvider.get()
                .faqBuilder(new FaqModule(fragment))
                .build();
    }

    public GuestInfoComponent getGuestInfoComponent(GuestInfoFragment fragment) {
        return mGuestInfoComponentProvider.get()
                .guestInfoBuilder(new GuestInfoModule(fragment))
                .build();
    }

    @Override
    public UsersComponent getUsersComponent(UsersActivity activity) {
        return mUsersComponentProvider.get()
                .usersBuilder(new UsersModule(activity))
                .build();
    }

    @Override
    public ProfileComponent getProfileComponent(ProfileFragment fragment) {
        return mProfileComponentProvider.get()
                .profileBuilder(new ProfileModule(fragment))
                .build();
    }

    @Override
    public TagsComponent getTagsComponent(TagsFragment fragment) {
        return mTagsComponentProvider.get()
                .tagsBuilder(new TagsModule(fragment))
                .build();
    }
}
