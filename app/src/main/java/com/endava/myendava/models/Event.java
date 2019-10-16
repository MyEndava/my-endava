package com.endava.myendava.models;

import com.endava.myendava.fragments.CalendarFragment;

import java.util.List;

public class Event implements CalendarFragment.EventsSection {

    private String title;
    private String duration;
    private List<Tag> tags;
    private int participantsNumber;
    private int eventCapacity;
    private String type;

    public Event(String title, String duration, List<Tag> tags, int participantsNr, int eventCapacity, String type) {
        this.title = title;
        this.duration = duration;
        this.tags = tags;
        this.participantsNumber = participantsNr;
        this.eventCapacity = eventCapacity;
        this.type = type;
    }

    public Event(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public String getType() {
        return type;
    }

    public String getFormattedTags() {
        String s = "";
        for (Tag tag : tags) {
            s += tag.getTagName() + ", ";
        }
        return s.substring(0, s.length() - 2);
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public String getName() {
        return title;
    }

    public int getEventCapacity() {
        return eventCapacity;
    }

    public void setEventCapacity(int eventCapacity) {
        this.eventCapacity = eventCapacity;
    }
}
