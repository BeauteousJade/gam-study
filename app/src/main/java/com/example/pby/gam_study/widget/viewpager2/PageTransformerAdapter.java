package com.example.pby.gam_study.widget.viewpager2;

import android.view.View;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

final class PageTransformerAdapter extends ViewPager2.OnPageChangeCallback {
    private final LinearLayoutManager mLayoutManager;

    private ViewPager2.PageTransformer mPageTransformer;

    PageTransformerAdapter(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    /**
     * Sets the PageTransformer. The page transformer will be called for each attached page whenever
     * the scroll position is changed.
     *
     * @param transformer The PageTransformer
     */
    void setPageTransformer(@Nullable ViewPager2.PageTransformer transformer) {
        // TODO: add support for reverseDrawingOrder: b/112892792
        // TODO: add support for pageLayerType: b/112893074
        mPageTransformer = transformer;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mPageTransformer == null) {
            return;
        }

        float transformOffset = -positionOffset;
        for (int i = 0; i < mLayoutManager.getChildCount(); i++) {
            View view = mLayoutManager.getChildAt(i);
            if (view == null) {
                throw new IllegalStateException(String.format(Locale.US,
                        "LayoutManager returned a null child at pos %d/%d while transforming pages",
                        i, mLayoutManager.getChildCount()));
            }
            int currPos = mLayoutManager.getPosition(view);
            float viewOffset = transformOffset + (currPos - position);
            mPageTransformer.transformPage(view, viewOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}