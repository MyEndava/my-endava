package com.endava.myendava;

import java.util.ArrayList;
import java.util.List;

public class TagsGenerator {

    public static List<Tag> generateTagsListForUser() {
        List<Tag> tagList = new ArrayList<>();
        tagList.addAll(generateTechnicalTags());
        tagList.addAll(generateSoftTags());
        List<Tag> interestedTags = new ArrayList<>();
        TagPurpose interestedTagPurpose = new TagPurpose("Interested");
        TagGroup techTagGroup = new TagGroup("Technical");
        TagGroup softTagGroup = new TagGroup("Soft");
        interestedTags.add(new Tag("Smart things", softTagGroup, interestedTagPurpose));
        interestedTags.add(new Tag("Kotlin", techTagGroup, interestedTagPurpose));
        interestedTags.add(new Tag("Open source", techTagGroup, interestedTagPurpose));
        tagList.addAll(interestedTags);
        return tagList;
    }

    public static List<Tag> generateTagsList() {
        List<Tag> tags = new ArrayList<>();
        tags.addAll(generateTechnicalTags());
        tags.addAll(generateSoftTags());
        return tags;
    }

    public static List<Tag> generateTechnicalTags() {
        TagGroup tagGroup = new TagGroup("Technical");
        TagPurpose tagPurpose = new TagPurpose("Mastering");
        List<Tag> technicalTags = new ArrayList<>();
        technicalTags.add(new Tag("Android", tagGroup, tagPurpose));
        technicalTags.add(new Tag("RX", tagGroup, tagPurpose));
        technicalTags.add(new Tag("SQLite", tagGroup, tagPurpose));
        technicalTags.add(new Tag("Dagger", tagGroup, tagPurpose));
        technicalTags.add(new Tag("MVP", tagGroup, tagPurpose));
        technicalTags.add(new Tag("Automation testing", tagGroup, tagPurpose));
        return technicalTags;
    }

    public static List<Tag> generateSoftTags() {
        TagGroup tagGroup = new TagGroup("Soft");
        TagPurpose tagPurpose = new TagPurpose("Mastering");
        List<Tag> softTags = new ArrayList<>();
        softTags.add(new Tag("Career coach", tagGroup, tagPurpose));
        softTags.add(new Tag("Scrum", tagGroup, tagPurpose));
        return softTags;
    }
}
