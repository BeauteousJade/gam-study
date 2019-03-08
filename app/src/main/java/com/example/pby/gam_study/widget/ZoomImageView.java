package com.example.pby.gam_study.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
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
    private boolean mIsCheckLeftAndRight;
    private boolean mIsCheckTopAndBottom;
    private boolean mInitializeDrawable;
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
                postDelayed(mAutoScaleRunnable, 16);
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
                mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                setImageMatrix(mMatrix);
                checkBoundAndCenterWhenScale();
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                float scale = getCurrentScale();
                if (scale > mMaxScale) {
                    mMatrix.postScale(mMaxScale / scale, mMaxScale / scale, detector.getFocusX(), detector.getFocusY());
                }
                if (scale < mInitScale) {
                    mMatrix.postScale(mInitScale / scale, mInitScale / scale, detector.getFocusX(), detector.getFocusY());
                }
                setImageMatrix(mMatrix);
                checkBoundAndCenterWhenScale();
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
        final float x = event.getX();
        final float y = event.getY();
        final RectF rectF = getMatrixRectF();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dX = mLastX - x;
                float dY = mLastY - y;
                mIsCheckLeftAndRight = true;
                mIsCheckTopAndBottom = true;
                boolean canMove = canMove(dX, dY);
                if (canMove) {
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
                break;
        }
        return true;
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

        private static final float SCALE_SMALL = 0.95f;
        private static final float SCALE_BIG = 1.05f;


        private float mPivotX;
        private float mPivotY;
        private float mTargetScale;
        private float mTempScale = 1.0f;
        private boolean mIsRunning;


        public void setPivot(float pivotX, float pivotY) {
            mPivotX = pivotX;
            mPivotY = pivotY;
        }

        public void setTargetScale(float targetScale) {
            final float currentScale = getCurrentScale();
            if (targetScale > currentScale) {
                mTempScale = SCALE_BIG;
            } else {
                mTempScale = SCALE_SMALL;
            }
            mTargetScale = targetScale;
        }

        @Override
        public void run() {
            mIsRunning = true;
            mMatrix.postScale(mTempScale, mTempScale, mPivotX, mPivotY);
            setImageMatrix(mMatrix);
            checkBoundAndCenterWhenScale();
            final float currentScale = getCurrentScale();
            if ((mTempScale > 1.0f && currentScale < mTargetScale) || (mTempScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
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
        }
    }
}
