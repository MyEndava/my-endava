package com.endava.myendava.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

public class KeyboardHelper {

    @Inject
    public KeyboardHelper() {
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
