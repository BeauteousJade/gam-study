package com.example.pby.gam_study.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.widget.item.ItemLayout;

public class MenuItemLayout extends ItemLayout {
    public MenuItemLayout(Context context) {
        super(context);
    }

    public MenuItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int top = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            top += lp.topMargin + lp.mItemOffsets[1];
            final int childTop = top;
            final int childBottom = top + child.getMeasuredHeight();
            final int childLeft = lp.leftMargin + lp.mItemOffsets[0];
            final int childRight = childLeft + child.getMeasuredWidth();
            child.layout(childLeft, childTop, childRight, childBottom);
            top = childBottom;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!ArrayUtil.isEmpty(mItemDecorationList)) {
            for (ItemDecoration itemDecoration : mItemDecorationList) {
                itemDecoration.onDraw(canvas);
            }
        }
    }
}
