package com.example.pby.gam_study.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class ForegroundImageView extends AppCompatImageView {

    private Drawable mDrawable;

    public ForegroundImageView(Context context) {
        super(context);
    }

    public ForegroundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ForegroundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setForegroundDrawable(Drawable drawable) {
        mDrawable = drawable;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawable.setBounds(0, 0, getWidth(), getHeight());
        mDrawable.draw(canvas);
    }
}
