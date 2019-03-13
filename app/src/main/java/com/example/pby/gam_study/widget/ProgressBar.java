package com.example.pby.gam_study.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.ResourcesUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class ProgressBar extends AppCompatTextView {

    private GradientDrawable mSelectDrawable;
    private GradientDrawable mUnSelectDrawable;

    private int mMaxProgress;
    private int mCurrentProgress;


    public ProgressBar(Context context) {
        super(context);
        initialize();
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {

        mSelectDrawable = new GradientDrawable();
        mSelectDrawable.setColor(ResourcesUtil.getColor(getContext(), R.color.color_main_blue));

        mUnSelectDrawable = new GradientDrawable();
        mUnSelectDrawable.setColor(ResourcesUtil.getColor(getContext(), R.color.color_item_dark));
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        mCurrentProgress = currentProgress;
        setText(String.format("%s / %s", mCurrentProgress, mMaxProgress));
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public int getCurrentProgress() {
        return mCurrentProgress;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mSelectDrawable.setCornerRadius(getMeasuredHeight() / 2);
        mUnSelectDrawable.setCornerRadius(getMeasuredHeight() / 2);
        mUnSelectDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setUpDrawableBounds();
        mUnSelectDrawable.draw(canvas);
        mSelectDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    private void setUpDrawableBounds() {
        mSelectDrawable.setBounds(0, 0, (int) (getMeasuredWidth() * mCurrentProgress * 1.0f / mMaxProgress), getMeasuredHeight());
    }
}