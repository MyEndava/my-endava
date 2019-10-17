package com.endava.myendava.activities;

import android.content.res.Configuration;
import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.endava.myendava.R;

public abstract class BaseFullScreenActivity extends BaseActivity {

    protected abstract ConstraintLayout getContainer();

    protected abstract int getBottomViewId();

    public void setNavigationBar() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        //todo Handle this for the phones that can hide the navigation bar from it
        if (isNavigationBarInvisible()) {
            setBottomNavigationConstraints(getNavigationBarHeight());
        }

        decorView.setOnSystemUiVisibilityChangeListener(
                (int visibility) -> {
                    if (visibility == 0) {
                        setBottomNavigationConstraints(getNavigationBarHeight());
                    } else {
                        setBottomNavigationConstraints(0);
                    }
                }
        );
    }

    private void setBottomNavigationConstraints(int bottomConstraintValue) {
        if (hasNavigationBar()) {
            ConstraintLayout constraintLayout = getContainer();
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(getBottomViewId(), ConstraintSet.BOTTOM,
                    constraintLayout.getId(), ConstraintSet.BOTTOM, bottomConstraintValue);
            constraintSet.applyTo(constraintLayout);
        }
    }

    private boolean isNavigationBarInvisible() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !hasMenuKey && !hasBackKey;
    }

    private int getNavigationBarHeight() {
        int result = 0;

        if (isNavigationBarInvisible()) {
            int orientation = getResources().getConfiguration().orientation;
            int resourceId = getResources().getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width", "dimen", "android");

            if (resourceId > 0) {
                return getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    private boolean hasNavigationBar() {
        int id = getResources().getIdentifier(getString(R.string.nav_bar_name),
                getString(R.string.boolean_def_type), getString(R.string.android_def_package));
        return id > 0 && getResources().getBoolean(id);
    }
}
