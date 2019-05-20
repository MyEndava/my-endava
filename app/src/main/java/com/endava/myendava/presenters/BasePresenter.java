package com.endava.myendava.presenters;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<View> {

    public abstract void viewReady();

    public WeakReference<View> mViewRef;

    public void setView(View view) {
        mViewRef = new WeakReference(view);
    }
}
