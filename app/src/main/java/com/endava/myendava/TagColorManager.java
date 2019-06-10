package com.endava.myendava;

public class TagColorManager {

    public static int getColor(String tagGroup) {
        switch (tagGroup) {
            case "Technical":
                return R.color.technical_tags;
            case "Soft":
                return R.color.soft_skills_tags;
            case "Interested":
                return R.color.interested_tags;
            default:
                return R.color.colorPrimary;
        }
    }
}
