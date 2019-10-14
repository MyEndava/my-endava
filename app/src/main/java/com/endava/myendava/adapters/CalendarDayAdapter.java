package com.endava.myendava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.models.CalendarDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayAdapter.CalendarViewHolder> {

    public static final String TRAINING_EVENT = "Training";
    public static final String CONFERENCE_EVENT = "Conference";
    public static final String CERTIFICATION_EVENT = "Certification";

    private final Context mContext;

    private List<CalendarDay> mCalendarDayList;

    public CalendarDayAdapter(Context context, List<CalendarDay> tags) {
        this.mContext = context;
        this.mCalendarDayList = tags;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.bind(mCalendarDayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCalendarDayList.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        @BindView(R.id.day_nr)
        public TextView mDayNr;
        @BindView(R.id.day)
        public TextView mDay;
        @BindView(R.id.dot1)
        public View mDot1;
        @BindView(R.id.dot2)
        public View mDot2;
        @BindView(R.id.dot3)
        public View mDot3;

        private List<View> dotList;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
            dotList = new ArrayList<>(Arrays.asList(mDot1, mDot2, mDot3));
        }

        public void bind(CalendarDay calendarDay) {
            mDayNr.setText(String.valueOf(calendarDay.getDayNr()));
            mDay.setText(calendarDay.getDay());
            for (String event : calendarDay.getEvents()) {
                View dot = dotList.get(calendarDay.getEvents().indexOf(event));
                dot.setBackgroundTintList(mContext.getColorStateList(getDotColor(event)));
                dot.setVisibility(View.VISIBLE);
            }
        }

        private int getDotColor(String event) {
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
}