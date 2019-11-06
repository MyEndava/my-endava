package com.endava.myendava.di.fragments;

import com.endava.myendava.fragments.CalendarFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {CalendarModule.class})
public interface CalendarComponent {

    void inject(CalendarFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        CalendarComponent.Builder calendarBuilder(CalendarModule module);

        CalendarComponent build();
    }
}
