package com.endava.myendava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.fragments.CalendarFragment;
import com.endava.myendava.models.Event;
import com.endava.myendava.models.EventTitle;
import com.endava.myendava.utils.EventState;
import com.endava.myendava.utils.EventsColorManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickHeaderItemDecoration.StickyHeaderInterface {

    public static final int TITLE_TYPE = 0;
    public static final int CONTENT_TYPE = 1;

    private Context context;
    private List<CalendarFragment.EventsSection> eventLists;
    private OnEventsAdapterListener listener;

    public EventsAdapter(Context context, List<CalendarFragment.EventsSection> eventLists, OnEventsAdapterListener listener) {
        this.context = context;
        this.eventLists = eventLists;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (eventLists.get(position).isHeader()) return TITLE_TYPE;
        return CONTENT_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TITLE_TYPE:
                return new TitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_title, parent, false));
            default:
                return new EventViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (eventLists.get(position).isHeader()) {
            ((TitleViewHolder) holder).bind((EventTitle) eventLists.get(position));
        } else {
            ((EventViewHolder) holder).bind((Event) eventLists.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return eventLists.size();
    }

    @Override
    public boolean isHeader(int itemPosition) {
        return eventLists.get(itemPosition).isHeader();
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.item_event_title;
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        do {
            if (this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition -= 1;
        } while (itemPosition >= 0);
        return headerPosition;
    }

    public void setList(List<CalendarFragment.EventsSection> list) {
        eventLists = list;
        notifyDataSetChanged();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        TextView date;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(EventTitle eventTitle) {
            date.setText(eventTitle.getTitle());
        }
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_title)
        TextView eventTitle;
        @BindView(R.id.event_type)
        View eventType;
        @BindView(R.id.event_time)
        TextView eventTime;
        @BindView(R.id.event_tags)
        TextView eventTags;
        @BindView(R.id.event_nr_people)
        TextView eventNumberPeople;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.heart_button)
        ImageButton favourite;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Event event) {
            eventTitle.setText(event.getTitle());
            eventTime.setText(event.getDuration());
            eventTags.setText(event.getFormattedTags());
            eventNumberPeople.setText(event.getParticipantsNumber() + " of " + event.getEventCapacity());
            int f = (int) ((event.getParticipantsNumber() / (double) event.getEventCapacity()) * 100);
            progressBar.setProgress(f);
            eventType.setBackgroundTintList(context.getColorStateList(EventsColorManager.getEventColor(event.getType())));
            favourite.setOnClickListener(v -> {
                if (event.getState() == EventState.UNCHECKED) {
                    favourite.setImageResource(R.drawable.ic_red_heart);
                    listener.setEventState(getAdapterPosition(), EventState.CHECKED);
                } else {
                    favourite.setImageResource(R.drawable.ic_empty_heart);
                    listener.setEventState(getAdapterPosition(), EventState.UNCHECKED);
                }
            });
        }
    }

    public interface OnEventsAdapterListener {
        void setEventState(int position, EventState eventState);
    }
}