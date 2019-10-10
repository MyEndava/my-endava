package com.endava.myendava.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.endava.myendava.R;
import com.endava.myendava.adapters.ChipsAdapter;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.listeners.OnChipClickedListener;
import com.endava.myendava.listeners.OnProfileEditListener;
import com.endava.myendava.models.Profile;
import com.endava.myendava.models.Tag;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.utils.EmailType;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.viewmodels.ProfileViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends BaseFragment implements OnChipClickedListener, OnProfileEditListener {

    @Inject
    MySharedPreferences mSharedPreferences;

    @Inject
    ProfileViewModel mProfileViewModel;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.name_text_view)
    TextView mNameTextView;

    @BindView(R.id.grade_text_view)
    TextView mGradeTextView;

    @BindView(R.id.location_text_view)
    TextView mLocationTextView;

    @BindView(R.id.email_text_view)
    TextView mEmailTextView;

    @BindView(R.id.profile_picture_image_view)
    CircleImageView mPhotoImageView;

    @BindView(R.id.tags_recycler_view)
    RecyclerView mRecyclerView;

    private static final String ARG_EMAIL = "arg_email";
    private static final String ARG_IS_USER_PROFILE = "arg_is_user_profile";

    private OnProfileFragmentInteractionListener listener;
    private Map<String, List<Tag>> tagsMap = new LinkedHashMap<>();
    private ChipsAdapter adapter;
    private Unbinder mUnbinder;

    public ProfileFragment() {
        // required empty constructor
    }

    public static ProfileFragment newInstance(String email, boolean isUserProfile) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_EMAIL, email);
        arguments.putBoolean(ARG_IS_USER_PROFILE, isUserProfile);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(arguments);
        return profileFragment;
    }

    @Override
    public View provideFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        setViews();

        mProfileViewModel.getProfile(getCurrentEmail()).observe(this, this::populateViews);

        mProfileViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(mProgressBar);
            } else {
                hideProgressBar(mProgressBar);
            }
        });
        mProfileViewModel.getError().observe(this, this::displayError);
    }

    @SuppressLint("RestrictedApi")
    private void setViews() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getActivity().getApplicationContext();
        locator.getProfileComponent(this).inject(this);
    }

    private String getCurrentEmail() {
        String email;
        if (EmailType.OWN_EMAIL.getType().equals(getArguments().getString(ARG_EMAIL, ""))) {
            email = mSharedPreferences.getUserEmail();
        } else {
            email = getArguments().getString(ARG_EMAIL);
        }
        return email;
    }

    private void populateViews(Profile profile) {
        mNameTextView.setText(profile.getFirstname() + " " + profile.getLastName());
        mGradeTextView.setText(profile.getGrade());
        mEmailTextView.setText(profile.getEmail());
        mLocationTextView.setText(profile.getLocation());
        String url = RetrofitClient.TEST_URL + profile.getImageUrl();
        Glide.with(getContext()).load(url)
                .into(mPhotoImageView);
        adapter = new ChipsAdapter(getContext(), mSharedPreferences.getUserEmail(), false, profile, toTagsMap(profile.getTags()), this, this);
        mRecyclerView.setAdapter(adapter);
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
            listener.onSkillSelected(tag, R.id.navigation_profile);
        }
    }

    @Override
    public void onEditClick(boolean isEditable) {
        mLocationTextView.setEnabled(isEditable);
        mEmailTextView.setEnabled(isEditable);
    }

    public interface OnProfileFragmentInteractionListener {

        void onSkillSelected(Tag tag, int navigationId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mProfileViewModel.onCleared();
    }
}
