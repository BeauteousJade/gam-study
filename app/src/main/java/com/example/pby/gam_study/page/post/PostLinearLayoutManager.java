package com.example.pby.gam_study.page.post;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

public class PostLinearLayoutManager extends LinearLayoutManager {

    private boolean mCanScrollVertically = true;

    public PostLinearLayoutManager(Context context) {
        super(context);
    }

    public PostLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public PostLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setCanScrollVertically(boolean flag) {
        mCanScrollVertically = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return mCanScrollVertically;
    }
}
