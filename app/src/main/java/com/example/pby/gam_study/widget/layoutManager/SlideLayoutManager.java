package com.example.pby.gam_study.widget.layoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

public class SlideLayoutManager extends RecyclerView.LayoutManager {

    private static final float DEFAULT_SCALE = 0.5f;

    private int mMaxVisibleCount;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView mRecyclerView;

    private final View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                // 这里需要手动告诉ItemTouchHelper可以侧滑
                mItemTouchHelper.startSwipe(childViewHolder);
            }
            return false;
        }
    };

    public SlideLayoutManager(int maxVisibleCount, ItemTouchHelper itemTouchHelper, RecyclerView recyclerView) {
        mMaxVisibleCount = maxVisibleCount;
        mItemTouchHelper = itemTouchHelper;
        mRecyclerView = recyclerView;
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
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 5,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 5 + getDecoratedMeasuredHeight(view));
            // 给每个ItemView设置scale
            view.setScaleX((float) Math.pow(DEFAULT_SCALE, i));
            view.setScaleY((float) Math.pow(DEFAULT_SCALE, i));
            if (i == 0) {
                view.setOnTouchListener(mOnTouchListener);
            } else {
                // 由于ItemView会复用，所以一定要设置null
                view.setOnTouchListener(null);
            }
        }
    }
}