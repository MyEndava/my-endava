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

    public static int getChipTextColor(String tagSubCategory) {
        switch (tagSubCategory) {
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
            case "Logistics":
                return R.color.white;
            default:
                return R.color.white;
        }
    }

    public static int getChipBackgroundColor(String tagSubCategory) {
        switch (tagSubCategory) {
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
            case "Logistics":
                return R.color.colorPrimaryDark;
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

    public static int getTagGroupBackgroundColor(String subcategory) {
        switch (subcategory){
            case "Technical":
                return R.drawable.orange_tag_group_shape;
            case "Soft":
                return R.drawable.blue_tag_group_shape;
            case "Project":
                return R.drawable.white_tag_group_shape;
            case "Logistics":
                return R.drawable.white_tag_group_shape;
            default:
                return R.drawable.white_tag_group_shape;
        }
    }
}
