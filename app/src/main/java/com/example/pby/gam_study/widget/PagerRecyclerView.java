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

    public PagerRecyclerView(@NonNull Context context) {
        super(context);
    }

    public PagerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
}
