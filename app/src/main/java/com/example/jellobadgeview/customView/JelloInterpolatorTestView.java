package com.example.jellobadgeview.customView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

public class JelloInterpolatorTestView extends View {

    Path mPath;
    Paint mPathLinePaint;

    int mColor = 0xfff5af00; // yellow

    List<com.example.jellobadgeview.customView.JelloInterpolatorTestView.Point> points;

    public JelloInterpolatorTestView(Context context) {
        super(context);
        init();
    }


    public JelloInterpolatorTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPathLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathLinePaint.setColor(mColor);
        mPathLinePaint.setStrokeWidth(5);
        mPathLinePaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();

        points = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (points.size() > 0) {
            mPath.reset();
            mPath.moveTo(points.get(0).x, points.get(0).y);
            for (Point p : points) {
                mPath.lineTo(p.x, p.y);
            }
        }
        canvas.drawPath(mPath, mPathLinePaint);
    }

    public void animateJello() {

        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(500);
        animator.setInterpolator((Interpolator) input -> input);

        animator.setFloatValues(0, 1);
        animator.addUpdateListener(animation -> {
            float x = (Float) animation.getAnimatedValue();
            float y = 0.6f + (float) (Math.exp(-4 * x) * Math.sin(25.12 * x + 39.4));
            points.add(new Point(x * getWidth(), y * getHeight()));
            invalidate();
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                points.clear();
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.start();
    }

    class Point {
        float x;
        float y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
