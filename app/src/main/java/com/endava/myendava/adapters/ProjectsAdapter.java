package com.endava.myendava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.endava.myendava.R;
import com.endava.myendava.listeners.OnChipClickedListener;
import com.endava.myendava.models.Project;
import com.endava.myendava.models.Tag;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.utils.TagColorManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private List<Project> mProjects;
    private OnMyItemClickListener mItemListener;
    private OnChipClickedListener mChipListener;
    private Context mContext;
    private boolean mIsLoggedInAsGuest;

    public ProjectsAdapter(Context context, List<Project> projects, OnChipClickedListener listener, boolean isGuest) {
        mProjects = projects;
        mContext = context;
        mChipListener = listener;
        mIsLoggedInAsGuest = isGuest;
    }

    @NonNull
    @Override
    public ProjectsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_project_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Project project = mProjects.get(position);
        viewHolder.bind(project);
    }


    public void setData(List<Project> projects){
        mProjects = projects;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_view)
        CardView mView;

        @BindView(R.id.image)
        ImageView mImage;

        @BindView(R.id.name)
        TextView mName;

        @BindView(R.id.description)
        TextView mDescription;

        @BindView(R.id.tags_chip_group)
        ChipGroup mTagsChipGroup;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Project project) {
            mName.setText(project.getProjectName());
            mDescription.setText(project.getDescription());
            Glide.with(mContext).load(RetrofitClient.TEST_URL + project.getImageUrl())
                    .centerCrop()
                    .into(mImage);
            mView.setOnClickListener(v -> mItemListener.onMyItemClickListener(project.getProjectName()));
            if (!mIsLoggedInAsGuest) {
                mTagsChipGroup.removeAllViews();
                for (Tag tag : project.getTags()) {
                    mTagsChipGroup.addView(createChip(tag, mTagsChipGroup.getContext()));
                }
            } else {
                mTagsChipGroup.setVisibility(View.GONE);
            }
        }
    }

    public interface OnMyItemClickListener {
        void onMyItemClickListener(String projectName);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        mItemListener = listener;
    }

    private Chip createChip(Tag tag, Context context) {
        Chip chip = new Chip(context);
        chip.setText(tag.getTagName());
        chip.setChipBackgroundColorResource(TagColorManager.getBackgroundColorProject(tag.getSubcategory()));
        chip.setTextColor(context.getResources().getColor(android.R.color.white));
        chip.setOnClickListener(v -> mChipListener.onChipClicked(tag));
        return chip;
    }
}
