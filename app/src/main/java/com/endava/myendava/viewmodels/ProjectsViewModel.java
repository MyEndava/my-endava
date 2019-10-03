package com.endava.myendava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.endava.myendava.models.Project;
import com.endava.myendava.repositories.ProjectsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectsViewModel extends ViewModel {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final ProjectsRepository mRepo;
    private MutableLiveData<List<Project>> mProjects;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();

    @Inject
    public ProjectsViewModel(ProjectsRepository projectsRepository) {
        mRepo = projectsRepository;
    }

    public LiveData<List<Project>> getProjects() {
        if(mProjects == null){
            mProjects = new MutableLiveData<>();
            loadData();
        }
        return mProjects;
    }

    private void loadData() {
        mIsUpdating.setValue(true);

        Disposable observable = mRepo.getProjects()
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

    private void handleSuccess(List<Project> projects) {
        mProjects.setValue(projects);
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
