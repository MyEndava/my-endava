package com.endava.myendava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.endava.myendava.models.Profile;
import com.endava.myendava.models.RemoveTagRequest;
import com.endava.myendava.models.RemoveTagResponse;
import com.endava.myendava.models.Tag;
import com.endava.myendava.repositories.ProfileRepository;
import com.endava.myendava.repositories.TagsRepository;
import com.endava.myendava.utils.MySharedPreferences;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final ProfileRepository profileRepository;
    private final TagsRepository tagsRepository;
    private MutableLiveData<Profile> mProfile;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mIsTagRemoved = new MutableLiveData<>();

    @Inject
    MySharedPreferences mSharedPreferences;

    @Inject
    public ProfileViewModel(ProfileRepository profileRepository,
                            TagsRepository tagsRepository) {
        this.profileRepository = profileRepository;
        this.tagsRepository = tagsRepository;
    }

    public LiveData<Profile> getProfile(String email) {
        if (mProfile == null) {
            mProfile = new MutableLiveData<>();
            loadData(email);
        }
        return mProfile;
    }

    private void onTagRemoved(RemoveTagResponse response) {
        if (response.success) {
            mIsTagRemoved.setValue("Tag removed successfully");
            loadData(mSharedPreferences.getUserEmail());
        } else {
            mIsTagRemoved.setValue("Sorry, an error occurred, try again later!");
        }
    }

    public LiveData<String> tagRemoved() {
        return mIsTagRemoved;
    }

    private void onTagRemoveError(Throwable throwable) {
    }

    public void removeTag(Tag tag) {
        mCompositeDisposable.add(tagsRepository.removeTagFromProfile(new RemoveTagRequest(tag.getTagId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTagRemoved, this::onTagRemoveError));
    }

    public void loadData(String email) {
        mIsUpdating.setValue(true);
        Disposable observable = profileRepository.getProfile(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess,
                        this::handleError);
        mCompositeDisposable.add(observable);
    }

    private void handleError(Throwable throwable) {
        Log.d(getClass().getSimpleName(), throwable.getLocalizedMessage());
        mIsUpdating.setValue(false);
        mError.setValue(throwable.getLocalizedMessage());
    }

    private void handleSuccess(Profile profile) {
        mProfile.setValue(profile);
        mIsUpdating.setValue(false);
        mError.setValue(null);
    }

    public LiveData<Boolean> isUpdating() {
        return mIsUpdating;
    }

    public LiveData<String> getError() {
        return mError;
    }

    @Override
    public void onCleared() {
        mCompositeDisposable.dispose();
    }
}