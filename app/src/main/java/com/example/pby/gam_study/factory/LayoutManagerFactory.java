package com.example.pby.gam_study.factory;

import android.content.Context;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LayoutManagerFactory {

    public static final SpanFull DEFAULT_SPAN_FULL = (adapter, position) -> adapter.isShowEmpty();

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

    public static RecyclerView.LayoutManager createVerticalLayoutManager(Context context) {
        return new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
    }


    public interface SpanFull {
        boolean onFull(BaseRecyclerAdapter adapter, int position);
    }

}
