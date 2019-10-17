package com.endava.myendava.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.models.Tag;
import com.endava.myendava.utils.TagColorManager;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagViewHolder> {

    public static final String CATEGORY_TECHNICAL = "Technical";
    public static final String CATEGORY_SOFT = "Soft";
    public static final String CATEGORY_PROJECT = "Project";
    public static final String CATEGORY_LOGISTICS = "Logistics";
    public static final String TAG_INFO_FIRST = "You and ";
    public static final String TAG_INFO_OTHERS = " others are using this tag.";

    private final Context mContext;

    private List<Tag> mTagsList;

    private OnTagClickListener mListener;

    public TagsAdapter(Context context, List<Tag> tags, OnTagClickListener listener) {
        this.mContext = context;
        this.mTagsList = tags;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.bind(mTagsList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mTagsList.size();
    }

    public void setData(List<Tag> faqs) {
        mTagsList = faqs;
        notifyDataSetChanged();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        @BindView(R.id.tag_title)
        public TextView mTagTitle;
        @BindView(R.id.tag_type)
        public TextView mTagType;
        @BindView(R.id.tag_info)
        public TextView mTagInfo;
        @BindView(R.id.check_icon)
        public ImageView mCheckIcon;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bind(Tag tag, OnTagClickListener listener) {
            Drawable background = itemView.getBackground();
            background.setColorFilter(mContext.getColor(TagColorManager.getColor(tag.getSubcategory())), PorterDuff.Mode.SRC);
            mTagTitle.setText(tag.getTagName());
            mTagType.setText(tag.getSubcategory());
            Drawable icon = null;
            switch (tag.getSubcategory()) {
                case CATEGORY_TECHNICAL:
                    icon = mContext.getResources().getDrawable(R.drawable.icons_filled_settings);
                    break;
                case CATEGORY_SOFT:
                    icon = mContext.getResources().getDrawable(R.drawable.icons_filled_comment);
                    break;
                case CATEGORY_PROJECT:
                    icon = mContext.getResources().getDrawable(R.drawable.icons_filled_tags);
                    break;
                case CATEGORY_LOGISTICS:
                    icon = mContext.getResources().getDrawable(R.drawable.ic_logistics);
                    break;
                default:
                    break;
            }
            mTagType.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            if (tag.getSubcategory().equals(CATEGORY_SOFT)) {
                setFieldsColor(mContext.getResources().getColor(R.color.secondary));
            } else {
                setFieldsColor(mContext.getResources().getColor(R.color.white));
            }
            Random random = new Random();
            String users = random.nextInt(2) == 1 ? TAG_INFO_FIRST : "";
            users += random.nextInt(100) + TAG_INFO_OTHERS;
            mTagInfo.setText(users);

            List<Tag> mSelectedTags = listener.getSelectedTags();
            if (mSelectedTags != null && mSelectedTags.contains(tag))
                mCheckIcon.setVisibility(View.VISIBLE);
            else mCheckIcon.setVisibility(View.GONE);

            itemView.setOnClickListener(v -> {
                listener.onTagClicked(tag);
                if (mCheckIcon.getVisibility() == View.VISIBLE) {
                    mCheckIcon.setVisibility(View.GONE);
                } else if (listener.isMultiSearchClicked()) {
                    mCheckIcon.setVisibility(View.VISIBLE);
                }
            });
        }

        private void setFieldsColor(int color) {
            mTagTitle.setTextColor(color);
            mTagType.setTextColor(color);
            mTagInfo.setTextColor(color);
            mCheckIcon.setColorFilter(color);
        }
    }

    public interface OnTagClickListener {

        void onTagClicked(Tag tag);

        List<Tag> getSelectedTags();

        boolean isMultiSearchClicked();
    }
}