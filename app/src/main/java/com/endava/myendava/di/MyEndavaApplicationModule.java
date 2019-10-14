package com.endava.myendava.di;

import android.content.Context;

import com.endava.myendava.app.MyEndavaApplication;
import com.endava.myendava.di.FragmentsDi.CalendarComponent;
import com.endava.myendava.di.FragmentsDi.FaqComponent;
import com.endava.myendava.di.FragmentsDi.GuestInfoComponent;
import com.endava.myendava.di.FragmentsDi.ProfileComponent;
import com.endava.myendava.di.FragmentsDi.TagsComponent;
import com.endava.myendava.di.activitiesDi.MainComponent;
import com.endava.myendava.di.activitiesDi.SignInAsGuestComponent;
import com.endava.myendava.di.activitiesDi.SignInComponent;
import com.endava.myendava.di.activitiesDi.SplashComponent;
import com.endava.myendava.di.activitiesDi.UsersComponent;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.utils.KeyboardHelper;
import com.endava.myendava.utils.MySharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.endava.myendava.utils.MySharedPreferences.MY_SHARED_PREFS_NAME;

@Module(subcomponents = {SplashComponent.class, SignInComponent.class, SignInAsGuestComponent.class,
        MainComponent.class, UsersComponent.class, CalendarComponent.class, FaqComponent.class,
        GuestInfoComponent.class, ProfileComponent.class, TagsComponent.class})
public class MyEndavaApplicationModule {

    private final MyEndavaApplication mApplication;

    public MyEndavaApplicationModule(MyEndavaApplication application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    MySharedPreferences provideSharedPreferences() {
        return new MySharedPreferences(mApplication.getApplicationContext().
                getSharedPreferences(MY_SHARED_PREFS_NAME, Context.MODE_PRIVATE));
    }

    @Singleton
    @Provides
    RetrofitClient provideRetrofitClient() {
        return new RetrofitClient();
    }

    @Singleton
    @Provides
    KeyboardHelper provideKeyboardHelper(){ return new KeyboardHelper();}

}
