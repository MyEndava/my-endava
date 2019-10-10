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
}
