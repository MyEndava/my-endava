package com.endava.myendava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.endava.myendava.models.User;
import com.endava.myendava.repositories.UsersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UsersViewModel extends ViewModel {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final UsersRepository mRepo;
    private MutableLiveData<List<User>> mUsers;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();

    @Inject
    public UsersViewModel(UsersRepository usersRepository) {
        mRepo = usersRepository;
    }

    public LiveData<List<User>> getUsersByTag(String tag) {
        if (mUsers == null) {
            mUsers = new MutableLiveData<>();
            loadData(tag);
        }
        return mUsers;
    }

    private void loadData(String tag) {
        mIsUpdating.setValue(true);

        Disposable observable = mRepo.getUsersByTag(tag)
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

    private void handleSuccess(List<User> users) {
        mUsers.setValue(users);
        mIsUpdating.setValue(false);
        mError.setValue(null);
    }

    @Override
    public void onCleared() {
        mCompositeDisposable.dispose();
    }
}
