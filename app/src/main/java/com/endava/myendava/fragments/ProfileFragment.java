package com.endava.myendava.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.endava.myendava.R;
import com.endava.myendava.adapters.ChipsAdapter;
import com.endava.myendava.listeners.OnChipClickedListener;
import com.endava.myendava.listeners.OnProfileEditedListener;
import com.endava.myendava.models.Profile;
import com.endava.myendava.models.RemoveTagRequest;
import com.endava.myendava.models.Tag;
import com.endava.myendava.models.UpdateProfileRequest;
import com.endava.myendava.rest.RetrofitClient;
import com.endava.myendava.utils.EmailType;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.viewmodels.ProfileViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends BaseFragment implements OnChipClickedListener, OnProfileEditedListener {

    @Inject
    MySharedPreferences mSharedPreferences;
    @Inject
    ProfileViewModel profileViewModel;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.name_text_view)
    TextView nameTextView;
    @BindView(R.id.grade_text_view)
    TextView gradeTextView;
    @BindView(R.id.location_text_view)
    TextView locationTextView;
    @BindView(R.id.email_text_view)
    TextView emailTextView;
    @BindView(R.id.profile_picture_image_view)
    CircleImageView photoImageView;
    @BindView(R.id.tags_recycler_view)
    RecyclerView tagsRecycler;

    private static final String ARG_EMAIL = "arg_email";
    private static final String ARG_IS_USER_PROFILE = "arg_is_user_profile";

    private OnProfileFragmentInteractionListener listener;
    private ChipsAdapter adapter;
    private Unbinder unbinder;
    private boolean isProfileEditable = false;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setViews();

        profileViewModel.getProfile(getCurrentEmail()).observe(this, this::populateViews);

        profileViewModel.getTagSubCategoriesLiveData().observe(this,
                tagSubCategories -> adapter.setData(tagSubCategories));

        profileViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar(progressBar);
            } else {
                hideProgressBar(progressBar);
            }
        });

        profileViewModel.getError().observe(this, this::displayError);
    }

    private void setViews() {
        tagsRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        tagsRecycler.setLayoutManager(layoutManager);
        adapter = new ChipsAdapter(getContext(), mSharedPreferences.getUserEmail(), isProfileEditable,
                new Profile(), new HashMap<>(), this, this);
        tagsRecycler.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void setupModule() {
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
        nameTextView.setText(profile.getFirstname() + " " + profile.getLastName());
        gradeTextView.setText(profile.getGrade());
        emailTextView.setText(profile.getEmail());
        locationTextView.setText(profile.getLocation());
        String url = RetrofitClient.TEST_URL + profile.getImageUrl();
        Glide.with(getContext()).load(url)
                .into(photoImageView);
        adapter.setData(profile);
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
            List<Tag> tagList = new ArrayList<>();
            tagList.add(tag);
            listener.onTagsSearch(tagList, R.id.navigation_profile);
        }
    }

    @Override
    public void onEditClicked(boolean isEditable, String description) {
        if (!isEditable) {
            updateProfile(description);
        }
    }

    private void updateProfile(String newDescription) {
        profileViewModel.updateProfile(new UpdateProfileRequest(getCurrentEmail(), newDescription))
                .observe(this, aBoolean -> {
                    if (aBoolean) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.profile_update_success), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.profile_update_error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onTagRemove(Tag tag) {
        profileViewModel.removeTag(new RemoveTagRequest(tag.getUserTagId())).observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(getActivity(), getResources().getString(R.string.tag_remove_success), Toast.LENGTH_SHORT).show();
                profileViewModel.loadData(getCurrentEmail());
                isProfileEditable = true;
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.tag_remove_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnProfileFragmentInteractionListener {

        void onTagsSearch(List<Tag> tagList, int navigationId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        profileViewModel.onCleared();
    }
}
