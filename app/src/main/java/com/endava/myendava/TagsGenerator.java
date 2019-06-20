package com.endava.myendava;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TagsGenerator {

    public static Map<String, List<Tag>> generateTagsListForUser() {
        Map<String, List<Tag>> tagsMap = new LinkedHashMap<>();
        tagsMap.put("Contact", generateContactTags());
        tagsMap.put("Project", generateProjectTags());
        tagsMap.put("Technical", generateTechnicalTags());
        tagsMap.put("Soft", generateSoftTags());
        List<Tag> interestedTags = new ArrayList<>();
        TagGroup techTagGroup = new TagGroup("Skill","Technical");
        TagGroup softTagGroup = new TagGroup("Skill","Soft");
        interestedTags.add(new Tag("Smart things", softTagGroup));
        interestedTags.add(new Tag("Kotlin", techTagGroup));
        interestedTags.add(new Tag("Open source", techTagGroup));
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
        TagGroup tagGroup = new TagGroup("Skill", "Technical");
        List<Tag> technicalTags = new ArrayList<>();
        technicalTags.add(new Tag("Android", tagGroup));
        technicalTags.add(new Tag("RX", tagGroup));
        technicalTags.add(new Tag("SQLite", tagGroup));
        technicalTags.add(new Tag("Dagger", tagGroup));
        technicalTags.add(new Tag("MVP", tagGroup));
        technicalTags.add(new Tag("Automation testing", tagGroup));
        return technicalTags;
    }

    public static List<Tag> generateSoftTags() {
        TagGroup tagGroup = new TagGroup("Skill","Soft");
        TagPurpose tagPurpose = new TagPurpose("Mastering");
        List<Tag> softTags = new ArrayList<>();
        softTags.add(new Tag("Career coach", tagGroup));
        softTags.add(new Tag("Scrum", tagGroup));
        return softTags;
    }

    public static List<Tag> generateContactTags() {
        TagGroup tagGroup = new TagGroup("Contact", null);
        List<Tag> softTags = new ArrayList<>();
        softTags.add(new Tag("alex.graur@endava.com", tagGroup));
        softTags.add(new Tag("en_agraur", tagGroup));
        return softTags;
    }

    public static List<Tag> generateProjectTags() {
        TagGroup tagGroup = new TagGroup("Project", null);
        List<Tag> projectTags = new ArrayList<>();
        projectTags.add(new Tag("SmartCredentials", tagGroup));
        return projectTags;
    }
}
