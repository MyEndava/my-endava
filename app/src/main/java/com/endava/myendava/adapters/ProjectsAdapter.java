package com.endava.myendava.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.OnChipClickedListener;
import com.endava.myendava.R;
import com.endava.myendava.Tag;
import com.endava.myendava.TagColorManager;
import com.endava.myendava.models.ProjectModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private final List<ProjectModel> mProjectsList;
    private OnMyItemClickListener mItemListener;
    private OnChipClickedListener mChipListener;
    private Context mContext;
    private boolean mIsLoggedInAsGuest;

    public ProjectsAdapter(Context context,OnChipClickedListener listener, boolean isGuest) {
        mProjectsList = new ArrayList<>();
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
        ProjectModel project = mProjectsList.get(position);
        viewHolder.bind(project);
    }

    @Override
    public int getItemCount() {
        return mProjectsList.size();
    }

    public void setData(List<ProjectModel> mData) {
        mProjectsList.clear();
        mProjectsList.addAll(mData);
        notifyDataSetChanged();
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

        private void bind(ProjectModel item) {
            mName.setText(item.getName());
            mDescription.setText(item.getDescription());
            mImage.setImageDrawable(new BitmapDrawable(mContext.getResources(), item.getImage()));
            mView.setOnClickListener(v -> mItemListener.onMyItemClickListener(item.getName()));
            if(!mIsLoggedInAsGuest){
                mTagsChipGroup.removeAllViews();
                for (Tag tag : item.getTags()) {
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
        chip.setChipBackgroundColorResource(TagColorManager.getColor(tag.getSubcategory()));
        chip.setTextColor(context.getResources().getColor(android.R.color.white));
        chip.setOnClickListener(v -> mChipListener.onChipClicked(tag));
        return chip;
    }
}
