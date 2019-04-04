package com.example.pby.gam_study.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import java.util.Objects;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinearLayoutManagerVerticalItemDecoration extends RecyclerView.ItemDecoration {

    private final GradientDrawable mDrawable;
    private final int mHeight;
    private OnItemOffsetListener mOnItemOffsetListener;
    private int mStartIndex;

    public LinearLayoutManagerVerticalItemDecoration(@ColorInt int color, int height) {
        mDrawable = new GradientDrawable();
        mDrawable.setColor(color);
        mHeight = height;
        mOnItemOffsetListener = null;
    }

    public LinearLayoutManagerVerticalItemDecoration(int startIndex, @ColorInt int color, int height) {
        this(color, height);
        mStartIndex = startIndex;
    }

    public LinearLayoutManagerVerticalItemDecoration(OnItemOffsetListener onItemOffsetListener, @ColorInt int color, int height) {
        this(Integer.MAX_VALUE, color, height);
        mOnItemOffsetListener = onItemOffsetListener;
    }

    @Override

    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        final int itemCount = Objects.requireNonNull(parent.getAdapter()).getItemCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int position = parent.getChildViewHolder(child).getAdapterPosition();
            if ((mOnItemOffsetListener != null && mOnItemOffsetListener.onItemOffset(position)) || position >= mStartIndex && position < itemCount - 1) {
                int top = child.getBottom();
                int bottom = top + mHeight;
                int left = child.getLeft();
                int right = child.getRight();
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        final int position = parent.getChildViewHolder(view).getAdapterPosition();
        final int itemCount = Objects.requireNonNull(parent.getAdapter()).getItemCount();
        if ((mOnItemOffsetListener != null && mOnItemOffsetListener.onItemOffset(position)) || position >= mStartIndex && position < itemCount - 1) {
            outRect.set(0, 0, 0, mHeight);
        }
    }

    public interface OnItemOffsetListener {
        boolean onItemOffset(int position);
    }
}
