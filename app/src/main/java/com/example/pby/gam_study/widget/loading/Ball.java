package com.example.pby.gam_study.widget.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.ResourcesUtil;

public class Ball extends View {

    private Paint mPaint;
    private int mLoadingColor;

    public Ball(Context context) {
        super(context);
        initialize();
    }

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public Ball(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        mLoadingColor = ResourcesUtil.getColor(getContext(), R.color.color_main_blue);
        mPaint = new Paint();
        mPaint.setColor(mLoadingColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, mPaint);
    }

    public void setLoadingColor(int color) {
        mLoadingColor = color;
        mPaint.setColor(color);
        initialize();
    }
}