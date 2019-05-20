package com.endava.myendava.di;

import android.content.Context;

import com.endava.myendava.app.MyEndavaApplication;
import com.endava.myendava.di.FragmentsDi.DashboardComponent;
import com.endava.myendava.di.FragmentsDi.HomeComponent;
import com.endava.myendava.di.FragmentsDi.NotificationsComponent;
import com.endava.myendava.di.activitiesDi.MainComponent;
import com.endava.myendava.di.activitiesDi.SignInAsGuestComponent;
import com.endava.myendava.di.activitiesDi.SignInComponent;
import com.endava.myendava.di.activitiesDi.SplashComponent;
import com.endava.myendava.utils.MySharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = {SplashComponent.class, SignInComponent.class, SignInAsGuestComponent.class,
        MainComponent.class, HomeComponent.class, DashboardComponent.class, NotificationsComponent.class})
public class MyEndavaApplicationModule {

    private final MyEndavaApplication mApplication;

    public MyEndavaApplicationModule(MyEndavaApplication application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    MySharedPreferences provideSharedPreferences() {
        return new MySharedPreferences(mApplication.getApplicationContext().
                getSharedPreferences("MyEndavaSharedPrefs", Context.MODE_PRIVATE));
    }
}
