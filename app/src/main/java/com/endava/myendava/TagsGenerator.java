package com.endava.myendava;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TagsGenerator {

    public static Map<String, List<Tag>> generateTagsListForUser() {
        Map<String, List<Tag>> tagsMap = new LinkedHashMap<>();
        tagsMap.put("Project", generateProjectTags());
        tagsMap.put("Technical", generateTechnicalTags());
        tagsMap.put("Soft", generateSoftTags());
        List<Tag> interestedTags = new ArrayList<>();
        interestedTags.add(new Tag("Skill", "Interested", null, -1,
                "Smart things", ""));
        interestedTags.add(new Tag("Skill", "Interested", null, -1,
                "Kotlin", ""));
        interestedTags.add(new Tag("Skill", "Interested", null, -1,
                "Open source", ""));
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
        technicalTags.add(new Tag("Skill", "Technical", null, -1,
                "Android", ""));
        technicalTags.add(new Tag("Skill", "Technical", null, -1,
                "RX", ""));
        technicalTags.add(new Tag("Skill", "Technical", null, -1,
                "SQLite", ""));
        technicalTags.add(new Tag("Skill", "Technical", null, -1,
                "Dagger", ""));
        technicalTags.add(new Tag("Skill", "Technical", null, -1,
                "MVP", ""));
        return technicalTags;
    }

    public static List<Tag> generateSoftTags() {
        List<Tag> softTags = new ArrayList<>();
        softTags.add(new Tag("Skill", "Soft", null, -1,
                "Career coach", ""));
        softTags.add(new Tag("Skill", "Soft", null, -1,
                "Scrum", ""));
        return softTags;
    }

    public static List<Tag> generateProjectTags() {
        List<Tag> projectTags = new ArrayList<>();
        projectTags.add(new Tag("Skill", "Interested", null, -1,
                "SmartCredentials", ""));
        return projectTags;
    }
}
