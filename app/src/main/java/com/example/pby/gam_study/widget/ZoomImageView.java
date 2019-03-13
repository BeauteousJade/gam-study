package com.example.pby.gam_study.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ZoomImageView extends ImageView {

    private static final int DEFAULT_MAX_RATIO = 4;
    private static final int DEFAULT_MID_RATIO = 2;


    private float mTouchSlop;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mInitScale;
    private float mMaxScale;
    private float mMidScale;
    private float mLastX;
    private float mLastY;
    private int mLastPointCount;
    private boolean mCanMove;
    private boolean mIsCheckLeftAndRight;
    private boolean mIsCheckTopAndBottom;
    private boolean mInitializeDrawable;
    private boolean mIsScaleByGesture;
    private ScaleGestureDetector mCurrentDetector;
    private AutoScaleRunnable mAutoScaleRunnable;
    private final Matrix mMatrix = new Matrix();


    public ZoomImageView(Context context) {
        super(context);
        initialize();
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        setScaleType(ScaleType.MATRIX);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mAutoScaleRunnable != null && mAutoScaleRunnable.isRunning()) {
                    return true;
                }
                if (mAutoScaleRunnable == null) {
                    mAutoScaleRunnable = new AutoScaleRunnable();
                }
                final float currentScale = getCurrentScale();
                final float x = e.getX();
                final float y = e.getY();
                if (currentScale >= mInitScale && currentScale < mMidScale) {
                    mAutoScaleRunnable.setPivot(x, y);
                    mAutoScaleRunnable.setTargetScale(mMidScale);
                } else {
                    mAutoScaleRunnable.setPivot(x, y);
                    mAutoScaleRunnable.setTargetScale(mInitScale);
                }
                postOnAnimation(mAutoScaleRunnable);
                return true;
            }
        });
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if (getDrawable() == null) {
                    return true;
                }
                float scaleFactor = detector.getScaleFactor();
                if (scaleFactor < 1.0f) {
                    // 使其缩放到一个点
                    scaleFactor *= 0.97f;
                }
                mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                setImageMatrix(mMatrix);
                checkBoundAndCenterWhenScale();
                mIsScaleByGesture = true;
                mCurrentDetector = detector;
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            }
        });

    }

    public float getCurrentScale() {
        final float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                initializeDrawable();
            }
        });
    }

    public void setDrawable(Drawable drawable) {
        setImageDrawable(drawable);
        post(this::initializeDrawable);
    }

    private void initializeDrawable() {
        final Drawable drawable = getDrawable();
        if (drawable == null | mInitializeDrawable) {
            return;
        }
        final int viewWidth = getMeasuredWidth();
        final int viewHeight = getMeasuredHeight();
        final int drawableWidth = drawable.getIntrinsicWidth();
        final int drawableHeight = drawable.getIntrinsicHeight();
        final float scale = Math.min(viewWidth * 1.0f / drawableWidth, viewHeight * 1.0f / drawableHeight);
        mInitScale = scale;
        mMaxScale = DEFAULT_MAX_RATIO * mInitScale;
        mMidScale = DEFAULT_MID_RATIO * mInitScale;
        mMatrix.postTranslate(viewWidth / 2.0f - drawableWidth * scale / 2, viewHeight / 2.0f - drawableHeight * scale / 2);
        mMatrix.postScale(scale, scale);
        setImageMatrix(mMatrix);
        checkBoundAndCenterWhenScale();
        mInitializeDrawable = true;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        end();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getDrawable() == null) {
            return super.onTouchEvent(event);
        }
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        mScaleGestureDetector.onTouchEvent(event);
        float x = 0;
        float y = 0;
        final int pointCount = event.getPointerCount();
        for (int i = 0; i < pointCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointCount * 1.0f;
        y /= pointCount * 1.0f;
        if (pointCount != mLastPointCount) {
            mCanMove = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointCount = pointCount;
        final RectF rectF = getMatrixRectF();
        final float currentScale = getCurrentScale();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                if (currentScale != mInitScale) {
                    Log.i("pby123", "down" + "width = " + rectF.width() + " height = " + rectF.height() + " width1 = " + getMeasuredWidth() + " height1 = " + getMeasuredHeight());
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentScale != mInitScale) {
                    Log.i("pby123", "down" + "width = " + rectF.width() + " height = " + rectF.height() + " width1 = " + getMeasuredWidth() + " height1 = " + getMeasuredHeight());
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                float dX = mLastX - x;
                float dY = mLastY - y;
                mIsCheckLeftAndRight = true;
                mIsCheckTopAndBottom = true;
                if (!mCanMove) {
                    mCanMove = canMove(dX, dY);
                }
                if (mCanMove) {
                    if (rectF.width() < getMeasuredWidth()) {
                        dX = 0;
                        mIsCheckLeftAndRight = false;
                    }
                    if (rectF.height() < getMeasuredHeight()) {
                        dY = 0;
                        mIsCheckTopAndBottom = false;
                    }
                    mMatrix.postTranslate(-dX * 1.66f, -dY * 1.6f);
                    setImageMatrix(mMatrix);
                    checkBoundWhenTranslate();
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resetIfScaleByGesture();
                break;
        }
        return true;
    }

    private void resetIfScaleByGesture() {
        if (mIsScaleByGesture) {
            final float currentScale = getCurrentScale();
            float targetScale = currentScale;
            if (currentScale > mMaxScale) {
                targetScale = mMaxScale;
            }
            if (currentScale < mInitScale) {
                targetScale = mInitScale;
            }
            if (targetScale != currentScale) {
                if (mAutoScaleRunnable == null) {
                    mAutoScaleRunnable = new AutoScaleRunnable();
                }
                mAutoScaleRunnable.setPivot(mCurrentDetector.getFocusX(), mCurrentDetector.getFocusY());
                mAutoScaleRunnable.setTargetScale(targetScale);
                mAutoScaleRunnable.setStep(0.3f);
                postOnAnimation(mAutoScaleRunnable);
            }
        }
        mIsScaleByGesture = false;
        mCurrentDetector = null;
    }

    public void end() {
        if (mAutoScaleRunnable != null) {
            mAutoScaleRunnable.end();
        }
    }

    private boolean canMove(float dX, float dY) {
        return Math.sqrt(dX * dX + dY * dY) >= mTouchSlop;
    }

    private void checkBoundWhenTranslate() {
        final RectF rectF = getMatrixRectF();
        final int viewWidth = getMeasuredWidth();
        final int viewHeight = getMeasuredHeight();
        float dX = 0;
        float dY = 0;
        if (rectF.left > 0 && mIsCheckLeftAndRight) {
            dX = -rectF.left;
        }
        if (rectF.right < viewWidth && mIsCheckLeftAndRight) {
            dX = viewWidth - rectF.right;
        }
        if (rectF.top > 0 && mIsCheckTopAndBottom) {
            dY = -rectF.top;
        }
        if (rectF.bottom < viewHeight && mIsCheckTopAndBottom) {
            dY = viewHeight - rectF.bottom;
        }
        mMatrix.postTranslate(dX, dY);
        setImageMatrix(mMatrix);
    }

    private void checkBoundAndCenterWhenScale() {
        final RectF rectF = getMatrixRectF();
        final int viewWidth = getMeasuredWidth();
        final int viewHeight = getMeasuredHeight();
        float dX = 0;
        float dY = 0;
        if (rectF.width() > viewWidth) {
            if (rectF.left > 0) {
                dX = -rectF.left;
            }
            if (rectF.right < viewWidth) {
                dX = viewWidth - rectF.right;
            }
        }
        if (rectF.height() > viewHeight) {
            if (rectF.top > 0) {
                dY = -rectF.top;
            }
            if (rectF.bottom < viewHeight) {
                dY = viewHeight - rectF.bottom;
            }
        }
        if (rectF.width() <= viewWidth) {
            dX = viewWidth / 2f - (rectF.left + rectF.right) / 2f;
        }
        if (rectF.height() <= viewHeight) {
            dY = viewHeight / 2f - (rectF.top + rectF.bottom) / 2f;
        }
        mMatrix.postTranslate(dX, dY);
        setImageMatrix(mMatrix);
    }

    private RectF getMatrixRectF() {
        RectF rectf = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectf.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            mMatrix.mapRect(rectf);
        }
        return rectf;
    }

    private class AutoScaleRunnable implements Runnable {

        private static final float DEFAULT_STEP = 0.08f;


        private float mPivotX;
        private float mPivotY;
        private float mTargetScale;
        private float mTempScale = 1.0f;
        private boolean mIsRunning;
        private float mStep = DEFAULT_STEP;


        public void setPivot(float pivotX, float pivotY) {
            mPivotX = pivotX;
            mPivotY = pivotY;
        }

        public void setTargetScale(float targetScale) {
            final float currentScale = getCurrentScale();
            if (targetScale > currentScale) {
                mTempScale = 1.0f + mStep;
            } else {
                mTempScale = 1.0f - mStep;
            }
            mTargetScale = targetScale;
        }

        public void setStep(float step) {
            mTempScale = 1.0f + step;
        }

        @Override
        public void run() {
            mIsRunning = true;
            mMatrix.postScale(mTempScale, mTempScale, mPivotX, mPivotY);
            setImageMatrix(mMatrix);
            checkBoundAndCenterWhenScale();
            final float currentScale = getCurrentScale();
            if ((mTempScale > 1.0f && currentScale < mTargetScale) || (mTempScale < 1.0f && currentScale > mTargetScale)) {
                postOnAnimation(this);
            } else {
                end();
            }
        }

        public boolean isRunning() {
            return mIsRunning;
        }

        public void end() {
            if (!mIsRunning) {
                return;
            }
            final float currentScale = getCurrentScale();
            mMatrix.postScale(mTargetScale / currentScale, mTargetScale / currentScale);
            setImageMatrix(mMatrix);
            checkBoundAndCenterWhenScale();
            mIsRunning = false;
            mStep = DEFAULT_STEP;
        }
    }
}
