package com.endava.myendava;

import java.io.Serializable;

public class Tag implements Serializable {

    private String title;
    private TagGroup tagGroup;

    public Tag(String title, TagGroup tagGroup) {
        this.title = title;
        this.tagGroup = tagGroup;
    }

    public String getTitle() {
        return title;
    }

    public TagGroup getTagGroup() {
        return tagGroup;
    }
}
