package com.endava.myendava.di.activities;

import com.endava.myendava.activities.UsersActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {UsersModule.class})
public interface UsersComponent {

    void inject(UsersActivity activity);

    @Subcomponent.Builder
    interface Builder {
        UsersComponent.Builder usersBuilder(UsersModule module);

        UsersComponent build();
    }
}