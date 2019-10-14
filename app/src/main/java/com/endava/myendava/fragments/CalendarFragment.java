package com.endava.myendava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.CalendarDayAdapter;
import com.endava.myendava.adapters.EventParentAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.models.CalendarDay;
import com.endava.myendava.models.Event;
import com.endava.myendava.models.EventParent;
import com.endava.myendava.models.Tag;
import com.endava.myendava.utils.MySharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalendarFragment extends BaseFragment {

    @Inject
    MySharedPreferences mSharedPreferences;

    @BindView(R.id.calendar_month_button)
    Button mCalendarMonthButton;
    @BindView(R.id.calendar_list)
    RecyclerView mCalendarRecycleView;
    @BindView(R.id.switch_my_events)
    SwitchCompat mSwitchEvents;
    @BindView(R.id.filters)
    Button mFiltersButton;
    @BindView(R.id.events_list_parent)
    RecyclerView mProjectsRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private EventParentAdapter mParentEventAdapter;

    private CalendarDayAdapter mCalendarDayAdapter;

    private Unbinder mUnbinder;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View provideFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        setMonthButton();
        setupCalendarDayAdapter();
        setupParentEventsAdapter();
    }

    private void setMonthButton() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(calendar.getTime());
        mCalendarMonthButton.setText(month_name + " " + calendar.get(Calendar.YEAR));
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) Objects.requireNonNull(getActivity()).getApplicationContext();
        locator.getDashboardComponent(this).inject(this);
    }

    private void setupParentEventsAdapter() {
        mParentEventAdapter = new EventParentAdapter(getContext(), getEventParentList());
        mProjectsRecyclerView.setAdapter(mParentEventAdapter);
        mProjectsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupCalendarDayAdapter() {
        mCalendarDayAdapter = new CalendarDayAdapter(getContext(), getCalendarDayList());
        mCalendarRecycleView.setAdapter(mCalendarDayAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mCalendarRecycleView.setLayoutManager(layoutManager);
    }

    private List<EventParent> getEventParentList() {
        EventParent eventParent1 = new EventParent("30 OCTOBER", getEventList().get(0));
        EventParent eventParent2 = new EventParent("31 OCTOBER", getEventList().get(1));
        EventParent eventParent3 = new EventParent("1 NOVEMBER", getEventList().get(2));
        return new ArrayList<>(Arrays.asList(eventParent1, eventParent2, eventParent3));
    }

    private List<List<Event>> getEventList() {
        List<Tag> tags = new ArrayList<>(Arrays.asList(new Tag("", "", "", 2, "Android", ""),
                new Tag("", "", "", 2, "MVP", "")));
        Event event1 = new Event("Training title", "2h 30", tags, 4, "Training");
        Event event2 = new Event("Certification title", "1h 30", tags, 2, "Certification");
        Event event3 = new Event("Conference title", "2h", tags, 9, "Conference");
        Event event4 = new Event("Training title", "3h 30", tags, 5, "Training");
        List<Event> list1 = new ArrayList<>();
        List<Event> list2 = new ArrayList<>(Arrays.asList(event1, event2, event3));
        List<Event> list3 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4));
        return new ArrayList<>(Arrays.asList(list1, list2, list3));
    }

    private List<CalendarDay> getCalendarDayList() {
        CalendarDay cd1 = new CalendarDay(1, "Mon", new ArrayList<>(Arrays.asList("Training", "Conference")));
        CalendarDay cd2 = new CalendarDay(2, "Tue", new ArrayList<>(Arrays.asList("Conference")));
        CalendarDay cd3 = new CalendarDay(3, "Wed", new ArrayList<>(Arrays.asList("Training", "Conference", "Certification")));
        CalendarDay cd4 = new CalendarDay(4, "Thu", new ArrayList<>(Arrays.asList("Certification")));
        CalendarDay cd5 = new CalendarDay(5, "Fri", new ArrayList<>(Arrays.asList("Certification", "Conference")));
        CalendarDay cd6 = new CalendarDay(6, "Sat", new ArrayList<>(Arrays.asList("Training")));
        CalendarDay cd7 = new CalendarDay(7, "Sun", new ArrayList<>(Arrays.asList("Certification", "Training")));
        CalendarDay cd8 = new CalendarDay(8, "Mon", new ArrayList<>(Arrays.asList("Conference")));
        CalendarDay cd9 = new CalendarDay(9, "Tue", new ArrayList<>());

        return new ArrayList<>(Arrays.asList(cd1, cd2, cd3, cd4, cd5, cd6, cd7, cd8, cd9));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
