package com.endava.myendava;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TagsGenerator {

    public static Map<String, List<Tag>> generateTagsMap() {
        Map<String, List<Tag>> tagsMap = new LinkedHashMap<>();
        tagsMap.put("Technical", generateTechnicalTags());
        tagsMap.put("Soft", generateSoftTags());
        List<Tag> interestedTags = new ArrayList<>();
        interestedTags.add(new Tag("Smart things"));
        interestedTags.add(new Tag("Kotlin"));
        interestedTags.add(new Tag("Open source"));
        tagsMap.put("Interested", interestedTags);
        return tagsMap;
    }

    public static List<Tag> generateTagsList() {
        List<Tag> tags = new ArrayList<>();
        tags.addAll(generateTechnicalTags());
        tags.addAll(generateSoftTags());
        return tags;
    }

    public static List<Tag> generateTechnicalTags() {
        List<Tag> technicalTags = new ArrayList<>();
        technicalTags.add(new Tag("Android"));
        technicalTags.add(new Tag("RX"));
        technicalTags.add(new Tag("SQLite"));
        technicalTags.add(new Tag("Dagger"));
        technicalTags.add(new Tag("MVP"));
        technicalTags.add(new Tag("Automation testing"));
        return technicalTags;
    }

    public static List<Tag> generateSoftTags() {
        List<Tag> softTags = new ArrayList<>();
        softTags.add(new Tag("Career coach"));
        softTags.add(new Tag("Scrum"));
        return softTags;
    }
}
