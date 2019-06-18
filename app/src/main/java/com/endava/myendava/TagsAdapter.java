package com.endava.myendava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagViewHolder> {

    private final Context context;
    private final List<Tag> tags;
    private OnTagClickListener listener;

    public TagsAdapter(Context context, List<Tag> tags, OnTagClickListener listener) {
        this.context = context;
        this.tags = tags;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.groupLetterIcon.setLetter(tags.get(position).getTagGroup().getName().substring(0, 1));
        holder.groupLetterIcon.setShapeColor(context.getResources().getColor(TagColorManager.getColor(tags.get(position).getTagGroup().getName())));
        holder.titleTextView.setText(tags.get(position).getTitle());
        Random random = new Random();
        String users = random.nextInt(2) == 1 ? "You and " : "";
        users += random.nextInt(100) + " others are using this tag.";
        holder.usersTextView.setText(users);
        holder.setOnTagClickListener(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public MaterialLetterIcon groupLetterIcon;
        public TextView titleTextView;
        public TextView usersTextView;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            groupLetterIcon = itemView.findViewById(R.id.tag_group_image_view);
            titleTextView = itemView.findViewById(R.id.tag_title_text_view);
            usersTextView = itemView.findViewById(R.id.tag_users_text_view);
        }

        public void setOnTagClickListener(final Tag tag) {
            itemView.setOnClickListener(v -> {
                listener.onTagClicked(tag);
            });
        }
    }

    public interface OnTagClickListener {

        void onTagClicked(Tag tag);
    }
}
