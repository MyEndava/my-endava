package com.endava.myendava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.TagCategoriesAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.models.SuggestTagRequest;
import com.endava.myendava.models.TagCategory;
import com.endava.myendava.viewmodels.TagsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alex.Graur@endava.com at 10/11/2019
 */
public class SuggestDialogFragment extends BottomSheetDialogFragment {

    public static final String TAG = "SuggestDialogFragment";
    private static final String ARG_TAG_CATEGORIES = "arg_tag_categories";

    @Inject
    TagsViewModel tagsViewModel;

    @BindView(R.id.suggest_tag_button)
    Button suggestTagButton;
    @BindView(R.id.tag_title_edit_text)
    EditText tagTitle;
    @BindView(R.id.tag_description_edit_text)
    EditText tagDescription;

    Unbinder unbinder;
    TagCategoriesAdapter tagCategoriesAdapter;

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
        unbinder = ButterKnife.bind(this, view);
        setupModule();
        setViews(view);
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getActivity().getApplicationContext();
        locator.getTagsComponent(this).inject(this);
    }

    private void setViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.categories_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        List<TagCategory> tagCategories = getArguments().getParcelableArrayList(ARG_TAG_CATEGORIES);
        tagCategoriesAdapter = new TagCategoriesAdapter(getContext(), tagCategories);
        recyclerView.setAdapter(tagCategoriesAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        suggestTagButton.setOnClickListener(view -> suggestTag());
    }

    private void suggestTag() {
        if (validateFields()) {
            tagsViewModel.suggestTag(new SuggestTagRequest(getTagName(), getTagDescription(), getTagCategoryId(), 1))
                    .observe(this, aBoolean -> {
                        if (aBoolean) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.tag_suggestion_success), Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.tag_suggestion_error), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_fill_all_the_fields), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFields() {
        return !tagTitle.getText().toString().isEmpty() && !tagDescription.getText().toString().isEmpty() &&
                tagCategoriesAdapter.getSelectedTag() != null;
    }

    private String getTagName() {
        return tagTitle.getText().toString();
    }

    private String getTagDescription() {
        return tagDescription.getText().toString();
    }

    private int getTagCategoryId() {
        return tagCategoriesAdapter.getSelectedTag().getId();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
