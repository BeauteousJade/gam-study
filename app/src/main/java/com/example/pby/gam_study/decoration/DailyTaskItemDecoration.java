package com.example.pby.gam_study.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

public class DailyTaskItemDecoration extends RecyclerView.ItemDecoration {

    private static final int TITLE_TOP = 20;
    private static final int ITEM_TOP = 10;
    private static final int DIVIDER_HEIGHT = 1;


    private int mTitleTop;
    private int mItemTop;
    private int mDividerHeight;
    private final GradientDrawable mDrawable;

    public DailyTaskItemDecoration(Context context) {
        mTitleTop = DisplayUtil.dpToPx(context, TITLE_TOP);
        mItemTop = DisplayUtil.dpToPx(context, ITEM_TOP);
        mDividerHeight = DisplayUtil.dpToPx(context, DIVIDER_HEIGHT);

        mDrawable = new GradientDrawable();
        mDrawable.setColor(ResourcesUtil.getColor(context, R.color.color_008));
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int position = parent.getChildViewHolder(child).getAdapterPosition();
            if (position > 1) {
                final int top = (child.getTop() + child.getTop() - mItemTop) / 2 - mDividerHeight / 2;
                final int bottom = top + mDividerHeight;
                final int left = child.getLeft();
                final int right = child.getRight();
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        final int position = parent.getChildViewHolder(view).getAdapterPosition();
        if (position == 0) {
            outRect.set(0, mTitleTop, 0, 0);
        } else {
            outRect.set(0, mItemTop, 0, 0);
        }
        if (parent.getAdapter() != null && parent.getAdapter().getItemCount() - 1 == position) {
            outRect.set(0, mItemTop, 0, mTitleTop);
        }
    }
}
