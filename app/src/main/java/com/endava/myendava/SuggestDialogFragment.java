package com.endava.myendava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.adapters.TagCategoriesAdapter;
import com.endava.myendava.models.TagCategory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex.Graur@endava.com at 10/11/2019
 */
public class SuggestDialogFragment extends BottomSheetDialogFragment {

    public static final String TAG = "SuggestDialogFragment";
    private static final String ARG_TAG_CATEGORIES = "arg_tag_categories";

    public static SuggestDialogFragment newInstance(ArrayList<TagCategory> tagCategories) {
        SuggestDialogFragment suggestDialogFragment = new SuggestDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARG_TAG_CATEGORIES, tagCategories);
        suggestDialogFragment.setArguments(arguments);
        return suggestDialogFragment;
    }

    public SuggestDialogFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_suggest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.categories_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        List<TagCategory> tagCategories = getArguments().getParcelableArrayList(ARG_TAG_CATEGORIES);
        TagCategoriesAdapter adapter = new TagCategoriesAdapter(getContext(), tagCategories);
        recyclerView.setAdapter(adapter);
    }
}
