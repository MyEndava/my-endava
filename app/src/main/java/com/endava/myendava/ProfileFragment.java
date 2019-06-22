package com.endava.myendava;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.utils.MySharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ProfileFragment extends Fragment implements OnChipClickedListener {

    private OnProfileFragmentInteractionListener listener;
    private Map<String, List<Tag>> tagsMap = new LinkedHashMap<>();
    private Profile profile;
    private ChipsAdapter adapter;
    private CompositeDisposable compositeDisposable;
    private RecyclerView recyclerView;
    private TextView nameTextView;
    private TextView gradeTextView;
    private TextView emailTextView;
    private TextView locationTextView;

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
        FloatingActionButton floatingActionButton = view.findViewById(R.id.add_tag_button);
        floatingActionButton.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "Add new tag", Toast.LENGTH_SHORT).show();
        });
        FloatingActionButton exportButton = view.findViewById(R.id.export_profile_button);
        exportButton.setOnClickListener(view12 -> Toast.makeText(getContext(), "Exported to PDF. Check your email.", Toast.LENGTH_SHORT).show());
        nameTextView = view.findViewById(R.id.name_text_view);
        gradeTextView = view.findViewById(R.id.grade_text_view);
        locationTextView = view.findViewById(R.id.location_text_view);
        emailTextView = view.findViewById(R.id.email_text_view);
        recyclerView = view.findViewById(R.id.tags_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChipsAdapter(getContext(), profile,
                tagsMap, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MySharedPreferences sharedPreferences = new MySharedPreferences(getContext()
                .getSharedPreferences("MyEndavaSharedPrefs", Context.MODE_PRIVATE));
        RetrofitClient retrofitClient = new RetrofitClient();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(retrofitClient.getRetrofitClient().getProfile(sharedPreferences.getUserEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                    nameTextView.setText(profile.getFirstname() + " " + profile.getLastname());
                    gradeTextView.setText(profile.getGrade());
                    emailTextView.setText(profile.getEmail());
                    locationTextView.setText(profile.getLocation());
                    adapter = new ChipsAdapter(getContext(), profile,
                            toTagsMap(profile.getTags()), this);
                    recyclerView.setAdapter(adapter);
                }, throwable -> {
                    Log.e("Profile", throwable.getLocalizedMessage());
                }));
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
            listener.onSkillSelected(tag);
        }
    }

    public interface OnProfileFragmentInteractionListener {

        void onSkillSelected(Tag tag);
    }
}
