package com.example.pby.gam_study.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.pby.gam_study.R;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ToggleButton extends View implements View.OnClickListener {

    private static final int OFFSET_CIRCLE = 5;


    private Drawable mUnCheckBackgroundDrawable;
    private Drawable mCheckedBackgroundDrawable;
    private float mInitOffset;
    private int mInitCircleColor;

    private Paint mPaint;
    private int mCurrentAlpha;
    private float mCurrentOffset;
    private float mCircleRadius;
    private boolean isChecked;

    private AnimateRunnable mCheckedRunnable;
    private AnimateRunnable mUnCheckedRunnable;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public ToggleButton(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToggleButton, defStyleAttr, defStyleRes);
        mInitOffset = typedArray.getDimension(R.styleable.ToggleButton_circleOffset, OFFSET_CIRCLE);
        mUnCheckBackgroundDrawable = typedArray.getDrawable(R.styleable.ToggleButton_unCheckBackgroundDrawable);
        mCheckedBackgroundDrawable = typedArray.getDrawable(R.styleable.ToggleButton_checkedBackgroundDrawable);
        mInitCircleColor = typedArray.getColor(R.styleable.ToggleButton_circleColor, Color.GREEN);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mInitCircleColor);
        mCurrentOffset = mInitOffset;
        mCurrentAlpha = 255;

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        mCircleRadius = (height - 2 * mInitOffset) / 2.0f;
        if (width >= height) {
            mCircleRadius = Math.min(mCircleRadius, width - 2 * mInitOffset / 3.0f);
        } else {
            mCircleRadius = width / 3.0f;
        }
        if (mCheckedRunnable == null) {
            mCheckedRunnable = new AnimateRunnable();
        }
        if (mUnCheckedRunnable == null) {
            mUnCheckedRunnable = new AnimateRunnable();
        }
        mCheckedRunnable.init(this, 255, 0, (int) mInitOffset, (int) (width - mInitOffset - 2 * mCircleRadius));
        mUnCheckedRunnable.init(this, 0, 255, (int) (width - mInitOffset - 2 * mCircleRadius), (int) mInitOffset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCurrentOffset + mCircleRadius, getHeight() / 2.f, mCircleRadius, mPaint);
    }

    private void drawBackground(Canvas canvas) {
        if (mCheckedBackgroundDrawable != null) {
            mCheckedBackgroundDrawable.setAlpha(255 - mCurrentAlpha);
            mCheckedBackgroundDrawable.setBounds(0, 0, getWidth(), getHeight());
            mCheckedBackgroundDrawable.draw(canvas);
        }
        if (mUnCheckBackgroundDrawable != null) {
            mUnCheckBackgroundDrawable.setAlpha(mCurrentAlpha);
            mUnCheckBackgroundDrawable.setBounds(0, 0, getWidth(), getHeight());
            mUnCheckBackgroundDrawable.draw(canvas);
        }

    }

    public void setChecked(final boolean checked) {
        isChecked = checked;
        post(() -> {
            mCurrentAlpha = isChecked ? 0 : 255;
            mCurrentOffset = isChecked ? (int) (getMeasuredWidth() - mInitOffset - 2 * mCircleRadius) : (int) mInitOffset;
            invalidate();
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChange(checked);
            }
        });
    }

    public void animationChangeChecked(boolean checked) {
        isChecked = checked;
        post(() -> {
            removeCallbacks(mCheckedRunnable);
            removeCallbacks(mUnCheckedRunnable);
            if (checked) {
                postOnAnimation(mCheckedRunnable);
            } else {
                postOnAnimation(mUnCheckedRunnable);
            }
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChange(checked);
            }
        });
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void onClick(View v) {
        animationChangeChecked(!isChecked);
    }

    private static class AnimateRunnable implements Runnable {

        private int mTargetAlpha;
        private int mTargetOffset;
        private float mAlphaStep;
        private float mOffsetStep;
        // 避免内存泄露
        private WeakReference<ToggleButton> mToggleButtonReference;

        public void init(ToggleButton toggleButton, int currentAlpha, int targetAlpha, int currentOffset, int targetOffset) {
            mToggleButtonReference = new WeakReference<>(toggleButton);
            mTargetAlpha = targetAlpha;
            mTargetOffset = targetOffset;
            mAlphaStep = (targetAlpha - currentAlpha) / 16.f;
            mOffsetStep = (targetOffset - currentOffset) / 16.f;
        }

        @Override
        public void run() {
            if (mToggleButtonReference.get() == null) {
                return;
            }
            ToggleButton toggleButton = mToggleButtonReference.get();
            toggleButton.mCurrentOffset += mOffsetStep;
            toggleButton.mCurrentAlpha += mAlphaStep;
            if (toggleButton.mCurrentOffset != mTargetOffset) {
                toggleButton.postOnAnimation(this);
            } else {
                toggleButton.mCurrentOffset = mTargetOffset;
                toggleButton.mCurrentAlpha = mTargetAlpha;
            }
            toggleButton.invalidate();
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(boolean checked);
    }
}
