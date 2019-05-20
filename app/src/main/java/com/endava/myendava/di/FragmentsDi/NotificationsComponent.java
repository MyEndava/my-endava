package com.endava.myendava.di.FragmentsDi;

import com.endava.myendava.fragments.NotificationsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {NotificationsModule.class})
public interface NotificationsComponent {

    void inject(NotificationsFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        NotificationsComponent.Builder notificationsBuilder(NotificationsModule module);

        NotificationsComponent build();
    }
}
