package com.endava.myendava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagViewHolder> {

    private final Context context;
    private final List<Tag> tags;

    public TagsAdapter(Context context, List<Tag> tags) {
        this.context = context;
        this.tags = tags;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.groupImageView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        holder.titleTextView.setText(tags.get(position).getTitle());
        Random random = new Random();
        String users = random.nextInt(2) == 1 ? "You and " : "";
        users += random.nextInt(100) + " others are using this tag.";
        holder.usersTextView.setText(users);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {

        public ImageView groupImageView;
        public TextView titleTextView;
        public TextView usersTextView;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            groupImageView = itemView.findViewById(R.id.tag_group_image_view);
            titleTextView = itemView.findViewById(R.id.tag_title_text_view);
            usersTextView = itemView.findViewById(R.id.tag_users_text_view);
        }
    }
}
