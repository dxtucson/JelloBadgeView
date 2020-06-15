package bouncing.jello.badge;

import android.view.animation.Interpolator;

public class JelloInterpolator implements Interpolator {

    private boolean highFreq;

    public JelloInterpolator(boolean highFreq) {
        this.highFreq = highFreq;
    }

    @Override
    public float getInterpolation(float input) {
        if (highFreq) return (float) (Math.exp(-4 * input) * Math.sin(50.24 * input + 39.4));
        return (float) (Math.exp(-4 * input) * Math.sin(25.12 * input + 39.4));
    }
}
