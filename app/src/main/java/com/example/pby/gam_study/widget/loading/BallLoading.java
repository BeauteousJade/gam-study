package com.example.pby.gam_study.widget.loading;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

public class BallLoading extends LinearLayout {

    private static final int DURATION = 400;
    private static final int SHAKE_DISTANCE = 2;
    private static final float PIVOT_X = 0.5f;
    private static final float PIVOT_Y = -3f;
    private static final int DEGREE = 30;
    private static final int COUNT_BALL = 5;
    private static final int DEFAULT_BALL_SIZE = 10;

    private boolean isStart = false;

    private RotateAnimation mRotateLeftAnimation;//cradleBallOne left to right
    private RotateAnimation mRotateRightAnimation;//cradleBallFive right to left
    private TranslateAnimation mShakeLeftAnimation;
    private TranslateAnimation mShakeRightAnimation;

    public BallLoading(Context context) {
        super(context);
        initialize();
    }

    public BallLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BallLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setOrientation(HORIZONTAL);
        int ballSize = DisplayUtil.dpToPx(getContext(), DEFAULT_BALL_SIZE);
        for (int i = 0; i < COUNT_BALL; i++) {
            addView(new Ball(getContext()), new LayoutParams(ballSize, ballSize));
        }
        initAnim();

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ResourcesUtil.getColor(getContext(), R.color.color_dialog));
        drawable.setCornerRadius(ResourcesUtil.getDimens(getContext(), R.dimen.radius));
        setBackground(drawable);
    }

    private void initAnim() {
        mRotateRightAnimation = new RotateAnimation(0, -DEGREE, RotateAnimation.RELATIVE_TO_SELF, PIVOT_X, RotateAnimation.RELATIVE_TO_SELF, PIVOT_Y);
        mRotateRightAnimation.setRepeatCount(1);
        mRotateRightAnimation.setRepeatMode(Animation.REVERSE);
        mRotateRightAnimation.setDuration(DURATION);
        mRotateRightAnimation.setInterpolator(new LinearInterpolator());
        mRotateRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isStart)
                    startRightAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mShakeLeftAnimation = new TranslateAnimation(0, SHAKE_DISTANCE, 0, 0);
        mShakeLeftAnimation.setDuration(DURATION);
        mShakeLeftAnimation.setInterpolator(new CycleInterpolator(2));

        mRotateLeftAnimation = new RotateAnimation(0, DEGREE, RotateAnimation.RELATIVE_TO_SELF, PIVOT_X, RotateAnimation.RELATIVE_TO_SELF, PIVOT_Y);
        mRotateLeftAnimation.setRepeatCount(1);
        mRotateLeftAnimation.setRepeatMode(Animation.REVERSE);
        mRotateLeftAnimation.setDuration(DURATION);
        mRotateLeftAnimation.setInterpolator(new LinearInterpolator());
        mRotateLeftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isStart) {
                    final int end = getChildCount() - 1;
                    for (int i = 1; i < end; i++) {
                        final View child = getChildAt(i);
                        child.startAnimation(mShakeLeftAnimation);
                    }
                    getChildAt(end).startAnimation(mRotateRightAnimation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        mShakeRightAnimation = new TranslateAnimation(0, -SHAKE_DISTANCE, 0, 0);
        mShakeRightAnimation.setDuration(DURATION);
        mShakeRightAnimation.setInterpolator(new CycleInterpolator(2));
        mShakeRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isStart) {
                    startLeftAnim();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void startLeftAnim() {
        getChildAt(0).startAnimation(mRotateLeftAnimation);
    }

    private void startRightAnim() {
        final int end = getChildCount() - 1;
        for (int i = 1; i < end; i++) {
            final View child = getChildAt(i);
            child.startAnimation(mShakeRightAnimation);
        }
    }


    public void start() {
        if (!isStart && getChildCount() >= 3) {
            isStart = true;
            startLeftAnim();
        }
    }

    public void stop() {
        isStart = false;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            child.clearAnimation();
        }
    }

    public boolean isStart() {
        return isStart;
    }

    public void setLoadingColor(int color) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child instanceof Ball) {
                ((Ball) child).setLoadingColor(color);
            }
        }
    }

    public void setBallSize(int size) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child instanceof Ball) {
                child.setLayoutParams(new LayoutParams(size, size));
            }
        }
        requestLayout();
    }

}