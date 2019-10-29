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
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.CalendarDayAdapter;
import com.endava.myendava.adapters.EventsAdapter;
import com.endava.myendava.adapters.StickHeaderItemDecoration;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.models.CalendarDay;
import com.endava.myendava.models.Event;
import com.endava.myendava.models.EventTitle;
import com.endava.myendava.utils.CalendarDummyDataGenerator;
import com.endava.myendava.utils.EventState;
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

public class CalendarFragment extends BaseFragment implements CalendarDayAdapter.OnCalendarDayAdapterListener, EventsAdapter.OnEventsAdapterListener {

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
    RecyclerView eventsRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private CalendarDayAdapter mCalendarDayAdapter;
    private EventsAdapter eventsAdapter;
    private LinearLayoutManager eventsLayoutManager;
    private Unbinder unbinder;
    private List<EventsSection> eventsList;
    LinkedHashMap<EventTitle, List<Event>> mockedEvents;

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
        unbinder = ButterKnife.bind(this, view);
        mockedEvents = calendarDummyDataGenerator.getMockedEvents();
        setMonthButton();
        setupCalendarDayAdapter();
        eventsList = new ArrayList<>();
        populateList();
        setupEventsAdapter();
        setupMyEventsSwitch();
    }

    private void setupMyEventsSwitch() {
        switchEvents.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (!isChecked) {
                List<EventsSection> favouriteEvents = new ArrayList<>();
                EventTitle title = null;
                for (EventsSection section : eventsList) {
                    if (section instanceof EventTitle) {
                        title = (EventTitle) section;
                    } else {
                        Event event = (Event) section;
                        if (event.getState() == EventState.CHECKED) {
                            if (!favouriteEvents.contains(title))
                                favouriteEvents.add(title);
                            favouriteEvents.add(event);
                        }
                    }
                }
                eventsAdapter.setList(favouriteEvents);
            } else {
                populateList();
                eventsAdapter.setList(eventsList);
            }
        });
    }

    private void populateList() {
        for (EventTitle eventTitle : mockedEvents.keySet()) {
            eventsList.add(eventTitle);
            for (Event event : mockedEvents.get(eventTitle)) {
                eventsList.add(event);
            }
        }
    }

    private void setupEventsAdapter() {
        eventsLayoutManager = new LinearLayoutManager(getContext());
        eventsRecyclerView.setLayoutManager(eventsLayoutManager);
        eventsAdapter = new EventsAdapter(getContext(), eventsList, this);
        eventsRecyclerView.setAdapter(eventsAdapter);
        StickHeaderItemDecoration stickHeaderDecoration = new StickHeaderItemDecoration(eventsAdapter);
        eventsRecyclerView.addItemDecoration(stickHeaderDecoration);
    }

    private void setMonthButton() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(calendar.getTime());
        calendarMonthButton.setText(month_name + " " + calendar.get(Calendar.YEAR));
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) Objects.requireNonNull(getActivity()).getApplicationContext();
        locator.getDashboardComponent(this).inject(this);
    }

    private void setupCalendarDayAdapter() {
        mCalendarDayAdapter = new CalendarDayAdapter(getContext(), calendarDummyDataGenerator.getMockedCalendarDays(), this);
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

    @Override
    public void scrollListToPosition(int position) {
        int newPos = 0, titleIndex = 0;
        CalendarDay day = calendarDummyDataGenerator.getMockedCalendarDays().get(position);
        for (EventTitle title : mockedEvents.keySet()) {
            if (title.getName().substring(0, 2).equals(day.getDayNumber() + "")) {
                break;
            }
            newPos += titleIndex + mockedEvents.get(title).size() + 1;
        }
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(newPos);
        eventsRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    @Override
    public void setEventState(int position, EventState eventState) {
        Event event = (Event) eventsList.get(position);
        event.setState(eventState);
    }

    public interface EventsSection {
        boolean isHeader();

        String getName();
    }
}
