package com.endava.myendava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.endava.myendava.models.SuggestTagRequest;
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
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsTagSuggested = new MutableLiveData<>();
    private MutableLiveData<List<TagCategory>> tagCategoriesLiveData;

    @Inject
    public TagsViewModel(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public LiveData<Boolean> suggestTag(SuggestTagRequest suggestTagRequest) {
        submitTag(suggestTagRequest);
        return mIsTagSuggested;
    }

    private void submitTag(SuggestTagRequest suggestTagRequest) {
        mIsUpdating.setValue(true);
        mCompositeDisposable.add(tagsRepository.suggestTag(suggestTagRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTagSuggested));
    }

    private void onTagSuggested() {
        mIsUpdating.setValue(false);
        mIsTagSuggested.setValue(true);
    }

    public LiveData<List<Tag>> getTags() {
        if (mTags == null) {
            mTags = new MutableLiveData<>();
            loadTagsData();
        }
        return mTags;
    }

    private void loadTagsData() {
        mIsUpdating.setValue(true);

        Disposable observable = tagsRepository.getTags()
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
        mTags.setValue(faqs);
        mIsUpdating.setValue(false);
        mError.setValue(null);
    }

    public LiveData<List<TagCategory>> getTagCategoriesLiveData() {
        tagCategoriesLiveData = new MutableLiveData<>();
        fetchTagCategories();
        return tagCategoriesLiveData;
    }

    private void fetchTagCategories() {
        Disposable observable = tagsRepository.getAllTagCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tagCategories -> tagCategoriesLiveData.setValue(tagCategories));
        mCompositeDisposable.add(observable);
    }

    @Override
    public void onCleared() {
        mCompositeDisposable.dispose();
    }
}
