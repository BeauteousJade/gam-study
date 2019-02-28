package com.example.pby.gam_study.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class PagerRecyclerView extends RecyclerView {

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
