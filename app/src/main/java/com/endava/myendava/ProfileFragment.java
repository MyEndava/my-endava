package com.endava.myendava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.endava.myendava.activities.FilteredTagsActivity;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.utils.MySharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ProfileFragment extends Fragment implements OnChipClickedListener {

    private static final String ARG_EMAIL = "arg_email";

    private OnProfileFragmentInteractionListener listener;
    private Map<String, List<Tag>> tagsMap = new LinkedHashMap<>();
    private Profile profile;
    private ChipsAdapter adapter;
    private CompositeDisposable compositeDisposable;
    private RecyclerView recyclerView;
    private CircleImageView photoImageView;
    private TextView nameTextView;
    private TextView gradeTextView;
    private TextView emailTextView;
    private TextView locationTextView;

    public ProfileFragment() {
        // required empty public constructor
    }

    public static ProfileFragment newInstance(String email) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_EMAIL, email);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(arguments);
        return profileFragment;
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
            Intent intent = new Intent(ProfileFragment.this.getContext(), FilteredTagsActivity.class);
            startActivity(intent);
        });
        FloatingActionButton exportButton = view.findViewById(R.id.export_profile_button);
        exportButton.setOnClickListener(view12 -> Toast.makeText(getContext(), "Exported to PDF. Check your email.", Toast.LENGTH_SHORT).show());
        nameTextView = view.findViewById(R.id.name_text_view);
        gradeTextView = view.findViewById(R.id.grade_text_view);
        locationTextView = view.findViewById(R.id.location_text_view);
        emailTextView = view.findViewById(R.id.email_text_view);
        photoImageView = view.findViewById(R.id.profile_picture_iamge_view);
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
        String email;

        if ("".equals(getArguments().getString(ARG_EMAIL, ""))) {
            MySharedPreferences sharedPreferences = new MySharedPreferences(getContext()
                    .getSharedPreferences("MyEndavaSharedPrefs", Context.MODE_PRIVATE));
            email = sharedPreferences.getUserEmail();
        } else {
            email = getArguments().getString(ARG_EMAIL);
        }

        RetrofitClient retrofitClient = new RetrofitClient();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(retrofitClient.getRetrofitClient().getProfile(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                    nameTextView.setText(profile.getFirstname() + " " + profile.getLastname());
                    gradeTextView.setText(profile.getGrade());
                    emailTextView.setText(profile.getEmail());
                    locationTextView.setText(profile.getLocation());
                    String url = RetrofitClient.TEST_URL + profile.getImageUrl();
                    Log.d("Profile", url);
                    Glide.with(getContext()).load(url)
                            .into(photoImageView);
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
