package com.endava.myendava.models;

import com.endava.myendava.fragments.CalendarFragment;

public class EventTitle implements CalendarFragment.EventsSection {

    private String title;

    public EventTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public String getName() {
        return title;
    }
}
