package com.endava.myendava.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.endava.myendava.models.LoginResult;
import com.endava.myendava.repositories.SignInRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignInViewModel extends ViewModel {
    private final SignInRepository mRepo;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isUserExistent = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();

    @Inject
    public SignInViewModel(SignInRepository mRepo) {
        this.mRepo = mRepo;
    }

    public LiveData<Boolean> checkUserEmail(String email) {
        mIsLoading.setValue(true);
        Disposable observable = mRepo.isUserExistent(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleError);
        mCompositeDisposable.add(observable);
        return this.isUserExistent;
    }

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public LiveData<String> getError() {
        return mError;
    }

    private void handleError(Throwable throwable) {
        mError.setValue(throwable.getLocalizedMessage());
    }

    private void handleSuccess(LoginResult isEmailValid) {
        isUserExistent.setValue(isEmailValid.isSuccess());
        mIsLoading.setValue(false);
        mError.setValue(null);
    }

    @Override
    public void onCleared() {
        mCompositeDisposable.dispose();
    }
}
