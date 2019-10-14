package com.endava.myendava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.models.EventParent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventParentAdapter extends RecyclerView.Adapter<EventParentAdapter.ProjectParentViewHolder> {

    private RecyclerView.RecycledViewPool mRecycledViewPool = new RecyclerView.RecycledViewPool();

    private Context mContext;

    private List<EventParent> mEventParents;

    public EventParentAdapter(Context context, List<EventParent> eventParents) {
        this.mContext = context;
        this.mEventParents = eventParents;
    }

    @NonNull
    @Override
    public ProjectParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_parent, parent, false);
        return new ProjectParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectParentViewHolder holder, int position) {
        holder.bind(mEventParents.get(position));
    }

    @Override
    public int getItemCount() {
        return mEventParents.size();
    }

    class ProjectParentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date)
        TextView mDate;
        @BindView(R.id.event_list)
        RecyclerView mEventsRecycleView;

        public ProjectParentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(EventParent parent) {
            mDate.setText(parent.getDate());
            mEventsRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
            mEventsRecycleView.setAdapter(new EventAdapter(mContext, parent.getEventList()));
            mEventsRecycleView.setRecycledViewPool(mRecycledViewPool);
        }
    }
}
