package com.example.pby.gam_study.factory;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;

public class LayoutManagerFactory {

    public static final SpanFull DEFAULT_SPAN_FULL = (adapter, position) -> {
        if (adapter.isShowEmpty()) {
            return true;
        }
        return false;
    };

    public static RecyclerView.LayoutManager createGridLayoutManagerIfEmpty(Context context, final BaseRecyclerAdapter adapter, final int spaCount, final SpanFull spanFull) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spaCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (spanFull.onFull(adapter, i)) {
                    return spaCount;
                }
                return 1;
            }
        });
        return gridLayoutManager;
    }

    public interface SpanFull {
        boolean onFull(BaseRecyclerAdapter adapter, int position);
    }

}
