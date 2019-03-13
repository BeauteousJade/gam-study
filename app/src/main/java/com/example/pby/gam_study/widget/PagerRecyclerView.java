package com.example.pby.gam_study.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class PagerRecyclerView extends RecyclerView {

    private int mDownX;
    private int mDownY;
    private PagerSnapHelper mPageSnapHelper;

    private OnPageScrollListener mOnPageScrollListener;

    private final RecyclerView.OnScrollListener mOnScrollChangeListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                final LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager && mOnPageScrollListener != null) {
                    mOnPageScrollListener.onPageSelected(recyclerView, ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition());
                }
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if (mOnPageScrollListener != null) {
                mOnPageScrollListener.onPageScroll(recyclerView, dx, dy);
            }
        }
    };

    public PagerRecyclerView(@NonNull Context context) {
        super(context);
        initialize();
    }

    public PagerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public PagerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final int moveX = (int) Math.abs(ev.getX() - mDownX);
                final int moveY = (int) Math.abs(ev.getY() - mDownY);
                if (moveX < moveY) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void initialize() {
        mPageSnapHelper = new PagerSnapHelper();
        mPageSnapHelper.attachToRecyclerView(this);
        addOnScrollListener(mOnScrollChangeListener);
    }

    public void setOnPageScrollListener(OnPageScrollListener listener) {
        mOnPageScrollListener = listener;
    }


    @FunctionalInterface
    public interface OnPageScrollListener {
        void onPageSelected(RecyclerView recyclerView, int position);

        default void onPageScroll(RecyclerView recyclerView, int dx, int dy) {

        }
    }
}
