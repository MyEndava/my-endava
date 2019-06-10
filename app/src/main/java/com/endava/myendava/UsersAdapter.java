package com.endava.myendava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private final Context context;
    private final List<User> users;

    public UsersAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.photoImageView.setImageDrawable(context.getDrawable(users.get(position).getPhotoId()));
        holder.nameTextView.setText(users.get(position).getName());
        holder.gradeTextView.setText(users.get(position).getGrade());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView photoImageView;
        public TextView nameTextView;
        public TextView gradeTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.user_photo_image_view);
            nameTextView = itemView.findViewById(R.id.user_name_text_view);
            gradeTextView = itemView.findViewById(R.id.user_grade_text_view);
        }
    }
}
