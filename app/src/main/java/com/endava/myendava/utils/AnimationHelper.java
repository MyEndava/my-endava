package com.endava.myendava.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

public class AnimationHelper {

    public static void animateFadeIn(View view, int duration) {
        animateFadeIn(view, duration, null);
    }

    public static void animateFadeIn(View view, int duration, Animation.AnimationListener listener) {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        animation.setFillAfter(true);
        if (listener != null) {
            animation.setAnimationListener(listener);
        }
        view.startAnimation(animation);
    }

    public static void animateScale(View view, float fromScale, float toScale, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, int duration) {
        animateScale(view, fromScale, toScale, pivotXType, pivotXValue, pivotYType, pivotYValue, duration, null);
    }

    public static void animateScale(View view, float fromScale, float toScale, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, int duration, Animation.AnimationListener listener) {
        ScaleAnimation animation = new ScaleAnimation(fromScale, toScale, fromScale, toScale, pivotXType, pivotXValue, pivotYType, pivotYValue);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(duration);
        if (listener != null) {
            animation.setAnimationListener(listener);
        }
        view.startAnimation(animation);
    }
}
