package com.endava.myendava.models;

import java.util.List;

public class Event {

    private String title;
    private String duration;
    private List<Tag> tags;
    private int participantsNr;
    private String type;

    public Event(String title, String duration, List<Tag> tags, int participantsNr, String type) {
        this.title = title;
        this.duration = duration;
        this.tags = tags;
        this.participantsNr = participantsNr;
        this.type = type;
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

    public int getParticipantsNr() {
        return participantsNr;
    }

    public void setParticipantsNr(int participantsNr) {
        this.participantsNr = participantsNr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormattedTags() {
        String s = "";
        for (Tag tag : tags) {
            s += tag.getTagName() + ", ";
        }
        return s.substring(0, s.length() - 2);
    }
}
