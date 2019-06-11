package com.endava.myendava;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ProfileFragment extends Fragment implements ChipsAdapter.OnChipClickedListener {

    private OnProfileFragmentInteractionListener listener;
    private ChipsAdapter adapter;

    public ProfileFragment() {
        // required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.tags_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Map<String, List<Tag>> tagsMap = new LinkedHashMap<>();
        for (Tag tag : TagsGenerator.generateTagsListForUser()) {
            if (!"Interested".equals(tag.getTagPurpose().getPurpose())) {
                if (tagsMap.containsKey(tag.getTagGroup().getName())) {
                    List<Tag> tags = tagsMap.get(tag.getTagGroup().getName());
                    tags.add(tag);
                    tagsMap.put(tag.getTagGroup().getName(), tags);
                } else {
                    List<Tag> tags = new ArrayList<>();
                    tags.add(tag);
                    tagsMap.put(tag.getTagGroup().getName(), tags);
                }
            }

            if ("Interested".equals(tag.getTagPurpose().getPurpose())) {
                if (tagsMap.containsKey(tag.getTagPurpose().getPurpose())) {
                    List<Tag> tags = tagsMap.get(tag.getTagPurpose().getPurpose());
                    tags.add(tag);
                    tagsMap.put(tag.getTagGroup().getName(), tags);
                } else {
                    List<Tag> tags = new ArrayList<>();
                    tags.add(tag);
                    tagsMap.put(tag.getTagPurpose().getPurpose(), tags);
                }
            }
        }
        adapter = new ChipsAdapter(getContext(), tagsMap, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnProfileFragmentInteractionListener) {
            listener = (OnProfileFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onChipClicked(Tag tag) {
        if (listener != null) {
            listener.onTagSelected(tag);
        }
    }

    public interface OnProfileFragmentInteractionListener {

        void onTagSelected(Tag tag);
    }
}
