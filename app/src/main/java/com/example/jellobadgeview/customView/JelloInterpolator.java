package com.example.jellobadgeview.customView;

import android.view.animation.Interpolator;

public class JelloInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.exp(-4 * input) * Math.sin(25.12 * input + 39.4));
    }
}
