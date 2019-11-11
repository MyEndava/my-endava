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
        return new ArrayList<>(Arrays.asList("18 OCTOBER", "19 OCTOBER", "20 OCTOBER", "21 OCTOBER",
                "22 OCTOBER", "23 OCTOBER", "24 OCTOBER", "25 OCTOBER", "26 OCTOBER", "27 OCTOBER"));
    }

    private List<List<Event>> getEventList() {
        List<Tag> androidTag = new ArrayList<>(Arrays.asList(new Tag("1", "1",
                "", 2, 5, "Android", "Android is a mobile operating system developed by Google. It is based on a modified version of the Linux kernel and other open source software, and is designed primarily for touchscreen mobile devices such as smartphones and tablets.")));
        List<Tag> iosTag = new ArrayList<>(Arrays.asList(new Tag("1", "1",
                "", 27, 5, "iOS", "iOS is a mobile operating system created and developed by Apple Inc. exclusively for its hardware. It is the operating system that presently powers many of the company's mobile devices, including the iPhone, and iPod Touch.")));
        List<Tag> careerCouchTag = new ArrayList<>(Arrays.asList(new Tag("1", "2",
                "", 16, 5, "Career Coach", "Random Career Coach Description")));
        List<Tag> benchTag = new ArrayList<>(Arrays.asList(new Tag("3", "5",
                "", 33, 5, "Bench", "Random Bench Description")));
        List<Tag> javaTag = new ArrayList<>(Arrays.asList(new Tag("1", "1",
                "", 31, 5, "Java", "Java is a general-purpose programming language that is class-based, object-oriented, and designed to have as few implementation dependencies as possible.")));
        List<Tag> phpTag = new ArrayList<>(Arrays.asList(new Tag("1", "1",
                "", 32, 5, "PHP", "Random php Description")));

        Event event1 = new Event("Swift Fundamentals", "3h", iosTag, 47, 60, "Training");
        Event event2 = new Event("Android Developer Fundamentals", "3h", androidTag, 66, 70, "Training");
        Event event3 = new Event("Situational Leadership", "2h", careerCouchTag, 13, 20, "Training");
        Event event4 = new Event("Pre-Employment Reading Materials", "3h", benchTag, 13, 50, "Training");
        Event event5 = new Event("Java Basics", "3h", javaTag, 73, 75, "Training");
        Event event6 = new Event("PHP Intermediate", "2h", phpTag, 86, 100, "Training");
        Event conf7 = new Event("Droidcon", "3h", androidTag, 1535, 1550, "Conference");
        Event conf8 = new Event("WWDC", "5h", iosTag, 75, 1500, "Conference");
        Event conf9 = new Event("GDG DevFest", "3h", androidTag, 420, 500, "Conference");
        Event conf10 = new Event("Pragma", "3h", iosTag, 300, 1300, "Conference");
        Event cert11 = new Event("Google Developers Certification", "12h", androidTag, 7, 10, "Certification");

        List<Event> list18 = new ArrayList<>(Arrays.asList(event3));
        List<Event> list19 = new ArrayList<>(Arrays.asList(cert11));
        List<Event> list20 = new ArrayList<>(Arrays.asList());
        List<Event> list21 = new ArrayList<>(Arrays.asList(event1, event4, event6));
        List<Event> list22 = new ArrayList<>(Arrays.asList(conf9, event5));
        List<Event> list23 = new ArrayList<>(Arrays.asList(conf7));
        List<Event> list24 = new ArrayList<>(Arrays.asList(conf10, event2));
        List<Event> list25 = new ArrayList<>(Arrays.asList());
        List<Event> list26 = new ArrayList<>(Arrays.asList());
        List<Event> list27 = new ArrayList<>(Arrays.asList(conf8));
        return new ArrayList<>(Arrays.asList(list18, list19, list20, list21, list22, list23, list24, list25, list26, list27));
    }

    public List<CalendarDay> getMockedCalendarDays() {
        CalendarDay cd18 = new CalendarDay(18, "Fri", new ArrayList<>(Arrays.asList("Training")));
        CalendarDay cd19 = new CalendarDay(19, "Sat", new ArrayList<>(Arrays.asList("Certification")));
        CalendarDay cd20 = new CalendarDay(20, "Sun", new ArrayList<>());
        CalendarDay cd21 = new CalendarDay(21, "Mon", new ArrayList<>(Arrays.asList("Training")));
        CalendarDay cd22 = new CalendarDay(22, "Tue", new ArrayList<>(Arrays.asList("Training", "Conference")));
        CalendarDay cd23 = new CalendarDay(23, "Wed", new ArrayList<>(Arrays.asList("Conference")));
        CalendarDay cd24 = new CalendarDay(24, "Thr", new ArrayList<>(Arrays.asList("Training", "Conference")));
        CalendarDay cd25 = new CalendarDay(25, "Fri", new ArrayList<>());
        CalendarDay cd26 = new CalendarDay(26, "Sat", new ArrayList<>());
        CalendarDay cd27 = new CalendarDay(27, "Sun", new ArrayList<>(Arrays.asList("Conference")));

        return new ArrayList<>(Arrays.asList(cd18, cd19, cd20, cd21, cd22, cd23, cd24, cd25, cd26, cd27));
    }
}
