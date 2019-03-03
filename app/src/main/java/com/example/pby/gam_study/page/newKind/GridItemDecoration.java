package com.example.pby.gam_study.page.newKind;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.ResourcesUtil;

public class GridItemDecoration extends ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        if (layoutManager != null) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
            if (spanSizeLookup != null) {
                int position = parent.getChildViewHolder(view).getAdapterPosition();
                int spanSize = spanSizeLookup.getSpanSize(position);
                if (spanSize == 1) {
                    outRect.top = ResourcesUtil.getDimens(parent.getContext(), R.dimen.item_margin);
                }
            }
        }
    }
}
