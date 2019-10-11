package com.endava.myendava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.endava.myendava.models.AddTagRequest;
import com.endava.myendava.models.Tag;
import com.endava.myendava.models.TagCategory;
import com.endava.myendava.repositories.TagsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TagsViewModel extends ViewModel {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final TagsRepository tagsRepository;
    private MutableLiveData<List<Tag>> mTags;
    private MutableLiveData<List<Tag>> mFilteredTags;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsTagAdded = new MutableLiveData<>();
    private MutableLiveData<List<TagCategory>> tagCategoriesLiveData = new MutableLiveData<>();
    private boolean areAllTags;

    @Inject
    public TagsViewModel(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public LiveData<Boolean> addTag(AddTagRequest addTagRequest) {
        insertTag(addTagRequest);
        return mIsTagAdded;
    }

    private void insertTag(AddTagRequest addTagRequest) {
        mIsUpdating.setValue(true);
        mIsTagAdded.setValue(false);
        mCompositeDisposable.add(tagsRepository.addTagToProfile(addTagRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTagAdded));
    }

    private void onTagAdded() {
        mIsTagAdded.setValue(true);
    }

    public LiveData<List<Tag>> getTags() {
        if (mTags == null) {
            mTags = new MutableLiveData<>();
            loadTagsData();
        }
        return mTags;
    }

    private void loadTagsData() {
        areAllTags = true;
        mIsUpdating.setValue(true);

        Disposable observable = tagsRepository.getTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess,
                        this::handleError);
        mCompositeDisposable.add(observable);
    }

    public LiveData<List<Tag>> getFilteredTags(String email) {
        if (mFilteredTags == null) {
            mFilteredTags = new MutableLiveData<>();
            loadFilteredTagsData(email);
        }
        return mFilteredTags;
    }

    private void loadFilteredTagsData(String email) {
        areAllTags = false;
        mIsUpdating.setValue(true);

        Disposable observable = tagsRepository.getFilteredTags(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess,
                        this::handleError);
        mCompositeDisposable.add(observable);
    }

    public LiveData<Boolean> isUpdating() {
        return mIsUpdating;
    }

    public LiveData<String> getError() {
        return mError;
    }

    private void handleError(Throwable throwable) {
        Log.d(getClass().getSimpleName(), throwable.getLocalizedMessage());
        mIsUpdating.setValue(false);
        mError.setValue(throwable.getLocalizedMessage());
    }

    private void handleSuccess(List<Tag> faqs) {
        if (areAllTags) {
            mTags.setValue(faqs);
        } else {
            mFilteredTags.setValue(faqs);
        }
        mIsUpdating.setValue(false);
        mError.setValue(null);
    }

    @Override
    public void onCleared() {
        mCompositeDisposable.dispose();
    }

    public MutableLiveData<List<TagCategory>> getTagCategoriesLiveData() {
        return tagCategoriesLiveData;
    }

    public void fetchTagCategories() {
        Disposable observable = tagsRepository.getAllTagCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tagCategories -> {
                    tagCategoriesLiveData.setValue(tagCategories);
                });
        mCompositeDisposable.add(observable);
    }
}
