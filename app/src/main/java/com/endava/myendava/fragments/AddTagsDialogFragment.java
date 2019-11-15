package com.endava.myendava.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.R;
import com.endava.myendava.adapters.FilteredChipsAdapter;
import com.endava.myendava.models.Tag;
import com.endava.myendava.utils.KeyboardHelper;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.viewmodels.TagsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddTagsDialogFragment extends BottomSheetDialogFragment implements FilteredChipsAdapter.OnTagClickListener {

    public static final String TAG = "AddTagDialogFragment";
    private static final String ARG_FILTERED_TAGS = "arg_filtered_tags";
    private static final String ARG_SUBCATEGORY_NAME = "arg_subcategory";

    @Inject
    MySharedPreferences mSharedPreferences;
    @Inject
    TagsViewModel tagsViewModel;
    @Inject
    KeyboardHelper keyboardHelper;

    @BindView(R.id.tags_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tag_group_name_text_view)
    TextView tagGroupName;
    @BindView(R.id.add_tag_button)
    Button addButton;
    @BindView(R.id.search)
    EditText searchBar;

    private Unbinder unbinder;
    private FilteredChipsAdapter adapter;
    private List<Tag> mSelectedTagsList = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    private OnTagsAddedListener onTagsAddedListener;

    public AddTagsDialogFragment() {
        // required empty constructor
    }

    public static AddTagsDialogFragment newInstance(ArrayList<Tag> tags, String subcategory, OnTagsAddedListener listener) {
        AddTagsDialogFragment addTagsDialogFragment = new AddTagsDialogFragment();
        addTagsDialogFragment.onTagsAddedListener = listener;
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARG_FILTERED_TAGS, tags);
        arguments.putString(ARG_SUBCATEGORY_NAME, subcategory);
        addTagsDialogFragment.setArguments(arguments);
        return addTagsDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setViews();
        searchBar.addTextChangedListener(getSearchWatcher());
        searchBar.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                keyboardHelper.hideSoftKeyboard(getActivity());
                return true;
            }
            return false;
        });
    }

    private TextWatcher getSearchWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<Tag> searchedTags = new ArrayList<>();
                for (Tag tag : tags) {
                    if (tag.getTagName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        searchedTags.add(tag);
                    }
                }
                adapter.setData(searchedTags);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private void setViews() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        tagGroupName.setText(new StringBuilder().append(Objects.requireNonNull(getArguments()).
                getString(ARG_SUBCATEGORY_NAME)).append(" Tags").toString());
        tags = getArguments().getParcelableArrayList(ARG_FILTERED_TAGS);
        adapter = new FilteredChipsAdapter(getContext(), tags, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTagClicked(Tag tag) {
        if (mSelectedTagsList.contains(tag)) {
            mSelectedTagsList.remove(tag);
        } else {
            mSelectedTagsList.add(tag);
        }
    }

    @Override
    public List<Tag> getSelectedTags() {
        return mSelectedTagsList;
    }

    @Override
    public void onResume() {
        super.onResume();
        addButton.setOnClickListener(view -> {
            onTagsAddedListener.onTagsAdded(mSelectedTagsList);
            dismiss();
        });
    }

    public interface OnTagsAddedListener {

        void onTagsAdded(List<Tag> tags);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
