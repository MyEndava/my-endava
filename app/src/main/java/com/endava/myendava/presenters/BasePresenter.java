package com.endava.myendava.presenters;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<View> {

    public WeakReference<View> mViewRef;

    public abstract void viewReady();

    public abstract void viewGone();

    public void setView(View view) {
        mViewRef = new WeakReference(view);
    }
}
