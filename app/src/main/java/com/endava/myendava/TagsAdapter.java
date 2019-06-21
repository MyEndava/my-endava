package com.endava.myendava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.List;
import java.util.Random;

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
        holder.bind(tags.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public MaterialLetterIcon groupImageView;
        public TextView titleTextView;
        public TextView usersTextView;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            groupImageView = itemView.findViewById(R.id.tag_group_image_view);
            titleTextView = itemView.findViewById(R.id.tag_title_text_view);
            usersTextView = itemView.findViewById(R.id.tag_users_text_view);
        }

        public void bind(Tag tag, OnTagClickListener listener) {
            groupImageView.setShapeColor(context.getResources()
                    .getColor(TagColorManager.getColor(tag.getSubcategory())));
            groupImageView.setLetter(tag.getSubcategory().substring(0, 1));
            titleTextView.setText(tag.getTagName());
            Random random = new Random();
            String users = random.nextInt(2) == 1 ? "You and " : "";
            users += random.nextInt(100) + " others are using this tag.";
            usersTextView.setText(users);
            itemView.setOnClickListener(v -> listener.onTagClicked(tag));
        }
    }

    public interface OnTagClickListener {

        void onTagClicked(Tag tag);
    }
}
