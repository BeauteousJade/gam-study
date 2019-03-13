package com.example.pby.gam_study.widget.layoutManager;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SlideLayoutManager extends RecyclerView.LayoutManager {


    @IntDef({Gravity.top, Gravity.bottom, Gravity.center})
    public @interface Gravity {
        int top = 0;
        int center = 1;
        int bottom = 2;
    }


    private static final float DEFAULT_SCALE = 0.5f;
    private int mMaxVisibleCount;
    private int mGravity;

    public SlideLayoutManager(int maxVisibleCount) {
        this(maxVisibleCount, Gravity.top);
    }

    public SlideLayoutManager(int maxVisibleCount, @Gravity int gravity) {
        mGravity = gravity;
        mMaxVisibleCount = maxVisibleCount;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int layoutCount = Math.min(getItemCount(), mMaxVisibleCount);
        detachAndScrapAttachedViews(recycler);
        for (int i = layoutCount - 1; i >= 0; i--) {
            final View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeightSpace(view);
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace + getDecoratedMeasuredHeight(view));
            // 给每个ItemView设置scale
            view.setScaleX((float) Math.pow(DEFAULT_SCALE, i));
            view.setScaleY((float) Math.pow(DEFAULT_SCALE, i));
        }
    }

    private int getHeightSpace(View itemView) {
        switch (mGravity) {
            case Gravity.bottom:
                return getHeight() - getDecoratedMeasuredHeight(itemView);
            case Gravity.center:
                return (getHeight() - getDecoratedMeasuredHeight(itemView)) / 2;
        }
        return 0;
    }
}