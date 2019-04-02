package com.example.pby.gam_study.other;

import android.graphics.Canvas;

import com.example.pby.gam_study.widget.layoutManager.ItemTouchStatus;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class GamItemTouchCallback extends ItemTouchHelper.Callback {
    private final ItemTouchStatus mItemTouchStatus;
    private final int mDefaultScrollX;
    private int mCurrentScrollX;
    private int mCurrentScrollXWhenInactive;
    private float mInitXWhenInactive;
    private boolean mFirstInactive;

    public GamItemTouchCallback(ItemTouchStatus itemTouchStatus, int defaultScrollX) {
        mItemTouchStatus = itemTouchStatus;
        mDefaultScrollX = defaultScrollX;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 上下拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        // 向左滑动
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dX == 0) {
            mCurrentScrollX = viewHolder.itemView.getScrollX();
            mFirstInactive = true;
        }
        if (isCurrentlyActive) {
            viewHolder.itemView.scrollTo(mCurrentScrollX + (int) -dX, 0);
        } else {
            if (mFirstInactive) {
                mFirstInactive = false;
                mCurrentScrollXWhenInactive = viewHolder.itemView.getScrollX();
                mInitXWhenInactive = dX;
            }
            if (viewHolder.itemView.getScrollX() >= mDefaultScrollX) {
                viewHolder.itemView.scrollTo(Math.max(mCurrentScrollX + (int) -dX, mDefaultScrollX), 0);
            } else {
                viewHolder.itemView.scrollTo((int) (mCurrentScrollXWhenInactive * dX / mInitXWhenInactive), 0);
            }
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder.itemView.getScrollX() > mDefaultScrollX) {
            viewHolder.itemView.scrollTo(mDefaultScrollX, 0);
        } else if (viewHolder.itemView.getScrollX() < 0) {
            viewHolder.itemView.scrollTo(0, 0);
        }
        mItemTouchStatus.onSaveItemStatus(viewHolder);
    }
}