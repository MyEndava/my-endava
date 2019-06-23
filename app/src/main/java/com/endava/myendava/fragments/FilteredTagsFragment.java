package com.endava.myendava.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.endava.myendava.AddTagRequest;
import com.endava.myendava.R;
import com.endava.myendava.Tag;
import com.endava.myendava.TagsAdapter;
import com.endava.myendava.TagsGenerator;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.rest.RetrofitServiceApi;
import com.endava.myendava.utils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FilteredTagsFragment extends Fragment implements TagsAdapter.OnTagClickListener {

    private List<Tag> tags = new ArrayList<>();
    private TagsAdapter adapter;
    private CompositeDisposable compositeDisposable;
    private RetrofitServiceApi retrofitServiceApi;
    private MySharedPreferences sharedPreferences;
    private OnFilteredTagsFragmentInteractionListener listener;

    public FilteredTagsFragment() {
    }

    public static FilteredTagsFragment newInstance() {
        return new FilteredTagsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filtered_tags, container, false);
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
        sharedPreferences = new MySharedPreferences(getContext()
                .getSharedPreferences("MyEndavaSharedPrefs", Context.MODE_PRIVATE));
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitServiceApi = retrofitClient.getRetrofitClient();
    }

    @Override
    public void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(retrofitServiceApi.getFilteredTags(sharedPreferences.getUserEmail())
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

        if (context instanceof FilteredTagsFragment.OnFilteredTagsFragmentInteractionListener) {
            listener = (FilteredTagsFragment.OnFilteredTagsFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onTagClicked(Tag tag) {
        compositeDisposable.add(retrofitServiceApi.addTagToProfile(new AddTagRequest(tag.getTagId(), 1, sharedPreferences.getUserEmail()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    listener.onTagAdded();
                    Log.d("TESTEST", "succes? ");
                }, throwable -> {
                    listener.onTagAdded();
                    Log.d("TESTEST", "error " + throwable);
                }));
    }

    public interface OnFilteredTagsFragmentInteractionListener {

        void onTagAdded();
    }
}
