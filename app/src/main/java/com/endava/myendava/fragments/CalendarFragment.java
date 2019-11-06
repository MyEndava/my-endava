package com.endava.myendava.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.CalendarDayAdapter;
import com.endava.myendava.adapters.EventsAdapter;
import com.endava.myendava.adapters.StickHeaderItemDecoration;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.models.Event;
import com.endava.myendava.models.EventTitle;
import com.endava.myendava.utils.CalendarDummyDataGenerator;
import com.endava.myendava.utils.MySharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalendarFragment extends BaseFragment {

    @Inject
    MySharedPreferences mSharedPreferences;
    @Inject
    CalendarDummyDataGenerator calendarDummyDataGenerator;

    @BindView(R.id.calendar_month_button)
    Button calendarMonthButton;
    @BindView(R.id.calendar_list)
    RecyclerView calendarRecycleView;
    @BindView(R.id.switch_my_events)
    SwitchCompat switchEvents;
    @BindView(R.id.filters)
    Button filtersButton;
    @BindView(R.id.events_list_parent)
    RecyclerView projectsRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private CalendarDayAdapter mCalendarDayAdapter;
    private Unbinder unbinder;
    private List<EventsSection> eventsList;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        setMonthButton();
        setupCalendarDayAdapter();
        eventsList = new ArrayList<>();
        populateList();
        setupEventsAdapter();
    }

    private void populateList() {
        LinkedHashMap<EventTitle, List<Event>> map = calendarDummyDataGenerator.getMockedEvents();
        for (EventTitle eventTitle : map.keySet()) {
            eventsList.add(eventTitle);
            for (Event event : map.get(eventTitle)) {
                eventsList.add(event);
            }
        }
    }

    private void setupEventsAdapter() {
        projectsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        EventsAdapter mofoAdapter = new EventsAdapter(getContext(), eventsList);
        projectsRecyclerView.setAdapter(mofoAdapter);
        StickHeaderItemDecoration stickHeaderDecoration = new StickHeaderItemDecoration(mofoAdapter);
        projectsRecyclerView.addItemDecoration(stickHeaderDecoration);
    }

    private void setMonthButton() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(calendar.getTime());
        calendarMonthButton.setText(month_name + " " + calendar.get(Calendar.YEAR));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_calendar;
    }

    public void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) Objects.requireNonNull(getActivity()).getApplicationContext();
        locator.getDashboardComponent(this).inject(this);
    }

    private void setupCalendarDayAdapter() {
        mCalendarDayAdapter = new CalendarDayAdapter(getContext(), calendarDummyDataGenerator.getMockedCalendarDays());
        calendarRecycleView.setAdapter(mCalendarDayAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        calendarRecycleView.setLayoutManager(layoutManager);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public interface EventsSection {
        boolean isHeader();

        String getName();
    }
}
