package com.endava.myendava.utils;

import com.endava.myendava.R;

public class TagColorManager {

    public static int getColor(String tagGroup) {
        switch (tagGroup) {
            case "Technical":
                return R.color.technical_tags;
            case "Soft":
                return R.color.soft_skills_tags;
            case "Interested":
                return R.color.interested_tags;
            case "Contact":
                return R.color.yellow;
            case "Project":
                return R.color.project_tags;
            case "Logistics":
                return R.color.contact_tags;
            default:
                return R.color.colorPrimary;
        }
    }

    public static int getTextChipColor(String tagGroup) {
        switch (tagGroup) {
            case "Technical":
                return R.color.technical_text_color;
            case "Soft":
                return R.color.technical_text_color;
            case "Interested":
                return R.color.endava_light_orange;
            case "Contact":
                return R.color.colorPrimary;
            case "Project":
                return R.color.white;
            default:
                return R.color.colorPrimary;
        }
    }

    public static int getBackgroundColor(String tagGroup) {
        switch (tagGroup) {
            case "Technical":
                return R.color.white;
            case "Soft":
                return R.color.white;
            case "Interested":
                return R.color.interested_tags;
            case "Contact":
                return R.color.contact_tags;
            case "Project":
                return R.color.colorPrimary;
            default:
                return R.color.colorPrimaryDark;
        }
    }
    public static int getBackgroundColorProject(String subcategory) {
        switch (subcategory) {
            case "Technical":
                return R.color.secondary;
            case "Soft":
                return R.color.soft_skills_tags;
            case "Interested":
                return R.color.interested_tags;
            case "Contact":
                return R.color.contact_tags;
            case "Project":
                return R.color.colorPrimary;
            default:
                return R.color.colorPrimaryDark;
        }
    }
}
