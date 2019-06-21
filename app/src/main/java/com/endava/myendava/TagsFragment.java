package com.endava.myendava;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.rest.RetrofitServiceApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class TagsFragment extends Fragment implements TagsAdapter.OnTagClickListener {

    private List<Tag> tags = new ArrayList<>();
    private TagsAdapter adapter;
    private CompositeDisposable compositeDisposable;
    private OnTagsFragmentInteractionListener listener;

    public TagsFragment() {
        // required empty public constructor
    }

    public static TagsFragment newInstance() {
        return new TagsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tags, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        RetrofitClient retrofitClient = new RetrofitClient();
        RetrofitServiceApi retrofitServiceApi = retrofitClient.getRetrofitClient();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(retrofitServiceApi.getAllTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tags -> {
                    this.tags.clear();
                    this.tags.addAll(tags);
                    adapter.notifyDataSetChanged();
                }, throwable -> {
                    Log.e("Tags", throwable.getLocalizedMessage());
                    Toast.makeText(getContext(), "Ooops! something wet wrong ...", Toast.LENGTH_SHORT).show();
                    this.tags.clear();
                    this.tags.addAll(TagsGenerator.generateTagsList());
                    adapter.notifyDataSetChanged();
                }));
    }

    @Override
    public void onPause() {
        compositeDisposable.clear();
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnTagsFragmentInteractionListener) {
            listener = (OnTagsFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        RecyclerView recyclerView = view.findViewById(R.id.tags_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TagsAdapter(getContext(), tags, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_profile, menu);
        MenuItem item = menu.findItem(R.id.search_item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_tag_item:

                if (listener != null) {
                    listener.onAddSkillClicked();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTagClicked(Tag tag) {
        if (listener != null) {
            listener.onSkillSelected(tag);
        }
    }

    private Map<String, List<Tag>> toTagsMap(List<Tag> tags) {
        Map<String, List<Tag>> tagsMap = new HashMap<>();
        for (Tag tag : tags) {
            addTagToMap(tagsMap, tag.getSubcategory(), tag);
        }
        return tagsMap;
    }

    private void addTagToMap(Map<String, List<Tag>> tagsMap, String key, Tag tag) {
        List<Tag> tags;
        if (tagsMap.containsKey(key)) {
            tags = tagsMap.get(key);
        } else {
            tags = new ArrayList<>();
        }
        tags.add(tag);
        tagsMap.put(key, tags);
    }

    public interface OnTagsFragmentInteractionListener {

        void onSkillSelected(Tag tag);

        void onAddSkillClicked();
    }
}
