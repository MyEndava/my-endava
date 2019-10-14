package com.endava.myendava.models;

import java.util.List;

public class EventParent {

    private String date;
    private List<Event> eventList;

    public EventParent(String date, List<Event> eventList) {
        this.date = date;
        this.eventList = eventList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
