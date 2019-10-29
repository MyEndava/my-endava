package com.endava.myendava.adapters;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.models.CalendarDay;
import com.endava.myendava.utils.EventsColorManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayAdapter.CalendarViewHolder> {

    private static final int ELEMENTS_TO_SHOW = 5;

    private final Context context;
    private List<CalendarDay> calendarDayList;
    private OnCalendarDayAdapterListener listener;

    public CalendarDayAdapter(Context context, List<CalendarDay> tags, OnCalendarDayAdapterListener listener) {
        this.context = context;
        this.calendarDayList = tags;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false);
        view.getLayoutParams().width = getRecyclerWidth() / 5;
        return new CalendarViewHolder(view);
    }

    private int getRecyclerWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float margins = (26 * 2 + 8 * ELEMENTS_TO_SHOW) * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return size.x - (int) margins;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.bind(calendarDayList.get(position));
    }

    @Override
    public int getItemCount() {
        return calendarDayList.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        @BindView(R.id.day_nr)
        public TextView dayNumber;
        @BindView(R.id.day)
        public TextView day;
        @BindView(R.id.dot1)
        public View dot1;
        @BindView(R.id.dot2)
        public View dot2;
        @BindView(R.id.dot3)
        public View dot3;

        private List<View> dotList;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
            dotList = new ArrayList<>(Arrays.asList(dot1, dot2, dot3));
            itemView.setOnClickListener(v->listener.scrollListToPosition(getAdapterPosition()));
        }

        public void bind(CalendarDay calendarDay) {
            dayNumber.setText(String.valueOf(calendarDay.getDayNumber()));
            day.setText(calendarDay.getDay());
            for (String event : calendarDay.getEvents()) {
                View dot = dotList.get(calendarDay.getEvents().indexOf(event));
                dot.setBackgroundTintList(context.getColorStateList(EventsColorManager.getEventColor(event)));
                dot.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface OnCalendarDayAdapterListener {
        void scrollListToPosition(int position);
    }
}