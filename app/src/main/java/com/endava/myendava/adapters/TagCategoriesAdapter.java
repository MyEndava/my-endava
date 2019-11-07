package com.endava.myendava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.models.TagCategory;

import java.util.List;

/**
 * Created by Alex.Graur@endava.com at 10/25/2019
 */
public class TagCategoriesAdapter extends RecyclerView.Adapter<TagCategoriesAdapter.TagCategoryViewHolder> {

    private final Context context;
    private final List<TagCategory> tagCategories;

    public TagCategoriesAdapter(final Context context, final List<TagCategory> tagCategories) {
        this.context = context;
        this.tagCategories = tagCategories;
    }

    @NonNull
    @Override
    public TagCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_category,
                parent, false);
        return new TagCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagCategoryViewHolder holder, int position) {
        holder.bind(tagCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return tagCategories.size();
    }

    public TagCategory getSelectedTag() {
        for (TagCategory everyTagCategory : tagCategories) {
            if (everyTagCategory.isSelected()) {
                return everyTagCategory;
            }
        }
        return null;
    }

    public class TagCategoryViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView titleTextView;
        private ImageView iconImageView;

        TagCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            titleTextView = itemView.findViewById(R.id.category_title_text_view);
            iconImageView = itemView.findViewById(R.id.category_icon_image_view);
        }

        public void bind(TagCategory tagCategory) {
            itemView.setOnClickListener(v -> {
                for (TagCategory everyTagCategory : tagCategories) {
                    if (!everyTagCategory.equals(tagCategory)) {
                        everyTagCategory.setSelected(false);
                    }
                }
                boolean isSelected = tagCategory.isSelected();
                tagCategory.setSelected(!isSelected);
                notifyDataSetChanged();
            });
            itemView.setSelected(tagCategory.isSelected());
            titleTextView.setText(tagCategory.getTitle());
            iconImageView.setImageDrawable(context.getDrawable(getDrawableByTagCategory(tagCategory.getTitle())));
        }

        //todo change the drawables when the backend implementation is done
        private int getDrawableByTagCategory(String category) {
            switch (category) {
                case "Technical Skill":
                    return R.drawable.ic_technical_tag_selector;
                case "Soft Skill":
                    return R.drawable.ic_technical_tag_selector;
                case "Project":
                    return R.drawable.ic_project_tag_selector;
                case "Service":
                    return R.drawable.ic_technical_tag_selector;
                case "Training":
                    return R.drawable.ic_soft_tag_selector;
                case "Conference":
                    return R.drawable.ic_soft_tag_selector;
                default:
                    return R.drawable.ic_technical_tag_selector;
            }
        }
    }
}
