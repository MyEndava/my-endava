package com.endava.myendava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.endava.myendava.models.Faq;
import com.endava.myendava.repositories.FaqRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FaqsViewModel extends ViewModel {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final FaqRepository mRepo;
    private MutableLiveData<List<Faq>> mFaqs;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();

    @Inject
    public FaqsViewModel(FaqRepository faqRepository) {
        mRepo = faqRepository;
    }

    public LiveData<List<Faq>> getFaqs() {
        if (mFaqs == null) {
            mFaqs = new MutableLiveData<>();
            loadData();
        }
        return mFaqs;
    }

    private void loadData() {
        mIsUpdating.setValue(true);

        Disposable observable = mRepo.getFaqs()
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

    private void handleSuccess(List<Faq> faqs) {
        mFaqs.setValue(faqs);
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
