package com.endava.myendava.models;

import java.util.List;

public class CalendarDay {
    private int dayNr;
    private String day;
    private List<String> events;

    public CalendarDay(int dayNr, String day, List<String> events) {
        this.dayNr = dayNr;
        this.day = day;
        this.events = events;
    }

    public int getDayNr() {
        return dayNr;
    }

    public void setDayNr(int dayNr) {
        this.dayNr = dayNr;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public void addEvent(String event) {
        events.add(event);
    }
}
