package com.example.pby.gam_study.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.pby.gam_study.widget.item.ItemLayout;

public class TabItemLayout extends ItemLayout {
    public TabItemLayout(Context context) {
        super(context);
    }

    public TabItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean interceptMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                heightSize = Math.min(heightSize, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
            }
            setMeasuredDimension(widthSize, heightSize);
            return true;
        }
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        final int avgWidth = getMeasuredWidth() / childCount;
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final int left = avgWidth * i;
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int childTop = lp.topMargin;
            final int childBottom = childTop + child.getMeasuredHeight();
            final int childLeft = left + lp.leftMargin + (avgWidth - child.getMeasuredWidth()) / 2;
            final int childRight = childLeft + child.getMeasuredWidth();

            child.layout(childLeft, childTop, childRight, childBottom);
        }
    }
}
