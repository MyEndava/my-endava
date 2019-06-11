package com.endava.myendava;

import java.io.Serializable;

public class Tag implements Serializable {

    private String title;
    private TagGroup tagGroup;
    private TagPurpose tagPurpose;

    public Tag(String title, TagGroup tagGroup, TagPurpose tagPurpose) {
        this.title = title;
        this.tagGroup = tagGroup;
        this.tagPurpose = tagPurpose;
    }

    public String getTitle() {
        return title;
    }

    public TagGroup getTagGroup() {
        return tagGroup;
    }

    public TagPurpose getTagPurpose() {
        return tagPurpose;
    }
}
