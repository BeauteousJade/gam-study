package com.example.pby.gam_study.widget.layoutManager;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.pby.gam_study.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

public class SlideItemTouchCallback extends ItemTouchHelper.Callback {

    private static final float DEFAULT_SCALE = 0.85f;
    private ItemTouchStatus mItemTouchStatus;
    private int mMaxVisibleCount;
    private List<OnSlideListener> mOnSlideListenerList;

    public SlideItemTouchCallback(ItemTouchStatus itemTouchStatus, int maxVisibleCount) {
        mItemTouchStatus = itemTouchStatus;
        mMaxVisibleCount = maxVisibleCount;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeFlag = mItemTouchStatus.isShowEmpty() ? 0 : ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlag);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (!ArrayUtil.isEmpty(mOnSlideListenerList)) {
            for (OnSlideListener listener : mOnSlideListenerList) {
                listener.onSlided(viewHolder.itemView.getTranslationX() > 0, viewHolder.getAdapterPosition());
            }
        }
        // 从数据源中移除相应的数据
        mItemTouchStatus.onItemRemove(viewHolder.getAdapterPosition());

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
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
            final int count = Math.min(recyclerView.getChildCount() - 1, mMaxVisibleCount - 1);
            for (int i = 0; i < count; i++) {
                // 下面的ItemView跟着手指缩放
                View child = recyclerView.getChildAt(i);
                final float currentScale = (float) Math.pow(DEFAULT_SCALE, count - i);
                final float nextScale = currentScale / DEFAULT_SCALE;
                final float scale = (nextScale - currentScale);
                child.setScaleX(Math.min(1, currentScale + scale * Math.abs(ratio)));
                child.setScaleY(Math.min(1, currentScale + scale * Math.abs(ratio)));
            }
            if (!ArrayUtil.isEmpty(mOnSlideListenerList)) {
                for (OnSlideListener listener : mOnSlideListenerList) {
                    listener.onSlide(dX, dY);
                }
            }
        }
    }

    public void addOnSlideListener(OnSlideListener listener) {
        if (mOnSlideListenerList == null) {
            mOnSlideListenerList = new ArrayList<>();
        }
        mOnSlideListenerList.add(listener);
    }

    public void removeOnSlideListener(OnSlideListener listener) {
        if (mOnSlideListenerList != null) {
            mOnSlideListenerList.remove(listener);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0f);
    }

    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

    public interface OnSlideListener {
        void onSlide(float dx, float dy);

        void onSlided(boolean right, int position);
    }
}
   