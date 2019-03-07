package com.example.pby.gam_study.widget.layoutManager;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class SlideItemTouchCallback extends ItemTouchHelper.Callback {

    private static final float DEFAULT_SCALE = 0.85f;
    private ItemTouchStatus mItemTouchStatus;
    private int mMaxVisibleCount;

    public SlideItemTouchCallback(ItemTouchStatus itemTouchStatus, int maxVisibleCount) {
        mItemTouchStatus = itemTouchStatus;
        mMaxVisibleCount = maxVisibleCount;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlag = mItemTouchStatus.isShowEmpty() ? 0 : ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // 从数据源中移除相应的数据
        mItemTouchStatus.onItemRemove(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // 跟着手指移动
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        final View itemView = viewHolder.itemView;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float ratio = dX / getThreshold(recyclerView, viewHolder);
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < -1) {
                ratio = -1;
            }
            // 跟着角度旋转
            itemView.setRotation(ratio * 15);
            final int count = Math.min(recyclerView.getChildCount(), mMaxVisibleCount - 1);
            for (int i = 0; i < count; i++) {
                // 下面的ItemView跟着手指缩放
                View child = recyclerView.getChildAt(i);
                final float currentScale = (float) Math.pow(DEFAULT_SCALE, 2 - i);
                final float nextScale = currentScale / DEFAULT_SCALE;
                final float scale = (nextScale - currentScale);
                child.setScaleX(Math.min(1, currentScale + scale * Math.abs(ratio)));
                child.setScaleY(Math.min(1, currentScale + scale * Math.abs(ratio)));
            }
        }
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0f);
    }

    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }
}
   