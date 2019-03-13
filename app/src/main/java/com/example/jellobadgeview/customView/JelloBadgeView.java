package com.example.jellobadgeview.customView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.jellobadgeview.R;

public class JelloBadgeView extends View {

    private static final float CORNER_PERCENT = 0.2f;
    private static final float PADDING_PERCENT = 0.2f;
    private static final float ANIMATION_PERCENT = 0.1f;
    private static final float TEXT_PADDING = 0.3f;

    Paint mBackgroundPaint;
    Paint mTextPaint;

    int mColor = 0xfff5af00; // yellow
    float mCorner, mJelloTop, mJelloBottom, mJelloLeft, mJelloRight, mPadding, mTextLeft, mTextBottom;
    RectF mRectF;

    String mText = null;
    Float textSizeInPixel = null;

    public JelloBadgeView(Context context) {
        super(context);
        init();
    }

    public JelloBadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setNumber(int number) {
        if (number < 100) this.mText = String.valueOf(number);
        else this.mText = "99+";
        textSizeInPixel = null;
        animateJello();
    }

    public void setColor(int color) {
        this.mColor = color;
        this.mBackgroundPaint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCorner = CORNER_PERCENT * getMeasuredHeight();
        mPadding = PADDING_PERCENT * getMeasuredHeight();
    }

    private void init() {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mColor);
        mRectF = new RectF();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);

        Typeface tf = ResourcesCompat.getFont(getContext(), R.font.lato_bold);
        mTextPaint.setTypeface(tf);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.set(mJelloLeft, mJelloTop, mJelloRight, mJelloBottom);
        canvas.drawRoundRect(mRectF, mCorner, mCorner, mBackgroundPaint);
        if (mText != null) {
            float textSize = measureTextSize();
            mTextPaint.setTextSize(textSize);
            // mTextLeft = getWidth() / 2f - mTextPaint.measureText(mText) / 2; // real x center
            mTextLeft = getWidth() / 2f - mTextPaint.measureText(mText) / 2 + mJelloLeft - mPadding; // A lil more cute
            mTextBottom = mJelloBottom - (mJelloBottom - mJelloTop) * TEXT_PADDING;
            canvas.drawText(mText, mTextLeft, mTextBottom, mTextPaint);
        }
    }

    float measureTextSize() {
        if (textSizeInPixel != null) return textSizeInPixel;
        float textMargin = getWidth() * TEXT_PADDING;
        textSizeInPixel = getWidth() - 2 * textMargin;
        mTextPaint.setTextSize(textSizeInPixel);
        float centerX = getWidth() / 2f;
        while ((centerX + mTextPaint.measureText(mText) / 2 > getWidth() - mPadding) || (centerX - mTextPaint.measureText(mText) / 2 < mPadding)) {
            textSizeInPixel -= 1f;
            mTextPaint.setTextSize(textSizeInPixel);
        }
        return textSizeInPixel;
    }

    void animateJello() {
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(500);
        animator.setInterpolator(new JelloInterpolator());
        animator.setFloatValues(0f, ANIMATION_PERCENT * getMeasuredWidth());
        animator.addUpdateListener(animation -> {
            mJelloLeft = mPadding + (Float) animation.getAnimatedValue();
            mJelloRight = getWidth() - mJelloLeft;
            mJelloTop = mPadding - (Float) animation.getAnimatedValue();
            mJelloBottom = getHeight() - mJelloTop;
            invalidate();
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mJelloLeft = mPadding;
                mJelloTop = mPadding;
                mJelloRight = getWidth() - mPadding;
                mJelloBottom = getHeight() - mPadding;
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
}
