package com.endava.myendava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.models.Event;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public static final String TRAINING_EVENT = "Training";
    public static final String CONFERENCE_EVENT = "Conference";
    public static final String CERTIFICATION_EVENT = "Certification";

    private Context mContext;

    private List<Event> mEvents;

    public EventAdapter(Context context, List<Event> events) {
        this.mContext = context;
        this.mEvents = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(mEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_title)
        TextView mEventTitle;
        @BindView(R.id.event_type)
        View mEventType;
        @BindView(R.id.event_time)
        TextView mEventTime;
        @BindView(R.id.event_tags)
        TextView mEventTags;
        @BindView(R.id.event_nr_people)
        TextView mEventNrPeople;
        @BindView(R.id.progressBar)
        ProgressBar mProgressBar;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Event event) {
            mEventTitle.setText(event.getTitle());
            mEventTime.setText(event.getDuration());
            mEventTags.setText(event.getFormattedTags());
            mEventNrPeople.setText(event.getParticipantsNr() + " of 10");
            int f = (int) ((event.getParticipantsNr() / 10.0) * 100);
            mProgressBar.setProgress(f);
            mEventType.setBackgroundTintList(mContext.getColorStateList(getEventColor(event.getType())));
        }

        private int getEventColor(String event) {
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
