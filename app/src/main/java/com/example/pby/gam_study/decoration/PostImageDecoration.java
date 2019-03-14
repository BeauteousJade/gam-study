package com.example.pby.gam_study.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import com.example.pby.gam_study.util.DisplayUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostImageDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_OFFSET = 5;

    private int mOffset;

    public PostImageDecoration(Context context) {
        mOffset = DisplayUtil.dpToPx(context, DEFAULT_OFFSET);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        final int position = parent.getChildViewHolder(view).getAdapterPosition();
        if (position > 0) {
            outRect.set(mOffset, 0, 0, 0);
        }
    }
}
