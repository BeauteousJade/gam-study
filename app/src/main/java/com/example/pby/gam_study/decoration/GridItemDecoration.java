package com.example.pby.gam_study.decoration;

import android.graphics.Rect;

import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.ResourcesUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {

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
