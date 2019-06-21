package com.endava.myendava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private final Context context;
    private final List<User> users;
    private final OnUserClickListener listener;

    public UsersAdapter(Context context, List<User> users, OnUserClickListener listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(users.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        CircleImageView photoImageView;
        TextView nameTextView;
        TextView gradeTextView;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            photoImageView = itemView.findViewById(R.id.user_photo_image_view);
            nameTextView = itemView.findViewById(R.id.user_name_text_view);
            gradeTextView = itemView.findViewById(R.id.user_grade_text_view);
        }

        void bind(User user, OnUserClickListener listener) {
            photoImageView.setImageDrawable(context.getDrawable(user.getPhotoId()));
            nameTextView.setText(new StringBuilder().append(user.getFirstName()).append(" ")
                    .append(user.getLastName()).toString());
            gradeTextView.setText(user.getGrade());
            itemView.setOnClickListener(view -> listener.onUserClicked(user));
        }
    }

    public interface OnUserClickListener {

        void onUserClicked(User user);
    }
}
