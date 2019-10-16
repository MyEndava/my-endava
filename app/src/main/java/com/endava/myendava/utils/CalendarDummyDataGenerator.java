package com.endava.myendava.utils;

import com.endava.myendava.models.CalendarDay;
import com.endava.myendava.models.Event;
import com.endava.myendava.models.EventTitle;
import com.endava.myendava.models.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

public class CalendarDummyDataGenerator {

    @Inject
    public CalendarDummyDataGenerator() {
    }

    public LinkedHashMap<EventTitle, List<Event>> getMockedEvents() {
        LinkedHashMap<EventTitle, List<Event>> map = new LinkedHashMap<>();
        for (String title : getEventsTitles()) {
            map.put(new EventTitle(title), getEventList().get(getEventsTitles().indexOf(title)));
        }
        return map;
    }

    private List<String> getEventsTitles() {
        return new ArrayList<>(Arrays.asList("30 OCTOBER", "31 OCTOBER", "1 NOVEMBER"));
    }

    private List<List<Event>> getEventList() {
        List<Tag> tags = new ArrayList<>(Arrays.asList(new Tag("", "", "", 2, "Android", ""),
                new Tag("", "", "", 2, "MVP", "")));
        Event event1 = new Event("Training title", "2h 30", tags, 4, 10, "Training");
        Event event2 = new Event("Certification title", "1h 30", tags, 2, 6, "Certification");
        Event event3 = new Event("Conference title", "2h", tags, 9, 15, "Conference");
        Event event4 = new Event("Training title", "3h 30", tags, 5, 16, "Training");
        List<Event> list1 = new ArrayList<>();
        List<Event> list2 = new ArrayList<>(Arrays.asList(event1, event2, event3));
        List<Event> list3 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4));
        return new ArrayList<>(Arrays.asList(list1, list2, list3));
    }

    public List<CalendarDay> getMockedCalendarDays() {
        CalendarDay cd0 = new CalendarDay(30, "Sun", new ArrayList<>());
        CalendarDay cd1 = new CalendarDay(1, "Mon", new ArrayList<>(Arrays.asList("Training", "Conference")));
        CalendarDay cd2 = new CalendarDay(2, "Tue", new ArrayList<>(Arrays.asList("Conference")));
        CalendarDay cd3 = new CalendarDay(3, "Wed", new ArrayList<>(Arrays.asList("Training", "Conference", "Certification")));
        CalendarDay cd4 = new CalendarDay(4, "Thu", new ArrayList<>(Arrays.asList("Certification")));
        CalendarDay cd5 = new CalendarDay(5, "Fri", new ArrayList<>(Arrays.asList("Certification", "Conference")));
        CalendarDay cd6 = new CalendarDay(6, "Sat", new ArrayList<>(Arrays.asList("Training")));
        CalendarDay cd7 = new CalendarDay(7, "Sun", new ArrayList<>(Arrays.asList("Certification", "Training")));
        CalendarDay cd8 = new CalendarDay(8, "Mon", new ArrayList<>(Arrays.asList("Conference")));
        CalendarDay cd9 = new CalendarDay(9, "Tue", new ArrayList<>());

        return new ArrayList<>(Arrays.asList(cd0, cd1, cd2, cd3, cd4, cd5, cd6, cd7, cd8, cd9));
    }
}
