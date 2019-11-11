package com.endava.myendava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.endava.myendava.models.Profile;
import com.endava.myendava.models.UpdateProfileRequest;
import com.endava.myendava.models.RemoveTagRequest;
import com.endava.myendava.models.TagSubCategory;
import com.endava.myendava.repositories.ProfileRepository;
import com.endava.myendava.repositories.TagsRepository;

import java.util.List;

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
    private MutableLiveData<Boolean> mIsTagRemoved = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsProfileUpdated = new MutableLiveData<>();
    private MutableLiveData<List<TagSubCategory>> tagSubCategoriesLiveData;

    @Inject
    public ProfileViewModel(ProfileRepository profileRepository, TagsRepository tagsRepository) {
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
        mIsUpdating.setValue(false);
        mError.setValue(throwable.getLocalizedMessage());
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage());
    }

    private void handleSuccess(Profile profile) {
        mProfile.setValue(profile);
        mIsUpdating.setValue(false);
        mError.setValue(null);
    }

    public LiveData<Boolean> removeTag(RemoveTagRequest removeTagRequest) {
        eraseTag(removeTagRequest);
        return mIsTagRemoved;
    }

    private void eraseTag(RemoveTagRequest removeTagRequest) {
        mIsUpdating.setValue(true);
        mCompositeDisposable.add(tagsRepository.removeTag(removeTagRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTagRemoveSuccess, this::onTagRemoveError));
    }

    private void onTagRemoveError(Throwable throwable) {
        mIsUpdating.setValue(false);
        mIsTagRemoved.setValue(false);
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage());
    }

    private void onTagRemoveSuccess() {
        mIsUpdating.setValue(false);
        mIsTagRemoved.setValue(true);
    }

    public LiveData<Boolean> updateProfile(UpdateProfileRequest updateProfileRequest) {
        renewProfile(updateProfileRequest);
        return mIsProfileUpdated;
    }

    private void renewProfile(UpdateProfileRequest updateProfileRequest) {
        mIsUpdating.setValue(true);
        mCompositeDisposable.add(profileRepository.updateProfile(updateProfileRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onProfileUpdatedSuccessfully, this::onProfileUpdatedError));
    }

    private void onProfileUpdatedError(Throwable throwable) {
        mIsUpdating.setValue(false);
        mIsProfileUpdated.setValue(false);
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage());
    }

    private void onProfileUpdatedSuccessfully() {
        mIsUpdating.setValue(false);
        mIsProfileUpdated.setValue(true);
    }

    public LiveData<List<TagSubCategory>> getTagSubCategoriesLiveData() {
        tagSubCategoriesLiveData = new MutableLiveData<>();
        fetchTagSubCategories();
        return tagSubCategoriesLiveData;
    }

    private void fetchTagSubCategories() {
        mIsUpdating.setValue(true);
        Disposable observable = tagsRepository.getAllTagSubCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::subCategoriesFetchSuccess, this::tagSubcategoriesFetchError);
        mCompositeDisposable.add(observable);
    }

    private void subCategoriesFetchSuccess(List<TagSubCategory> tagSubCategories) {
        tagSubCategoriesLiveData.setValue(tagSubCategories);
        mIsUpdating.setValue(false);
        mError.setValue(null);
    }

    private void tagSubcategoriesFetchError(Throwable throwable) {
        mIsUpdating.setValue(false);
        mError.setValue(throwable.getLocalizedMessage());
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage());
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