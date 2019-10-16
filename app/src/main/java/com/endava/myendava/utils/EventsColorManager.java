package com.endava.myendava.utils;

import com.endava.myendava.R;

public class EventsColorManager {

    public static final String TRAINING_EVENT = "Training";
    public static final String CONFERENCE_EVENT = "Conference";
    public static final String CERTIFICATION_EVENT = "Certification";

    public static int getEventColor(String event) {
        switch (event) {
            case TRAINING_EVENT:
                return R.color.primary;
            case CERTIFICATION_EVENT:
                return R.color.yellow;
            case CONFERENCE_EVENT:
                return R.color.blue2;
        }
        return R.color.secondary;
    }
}
