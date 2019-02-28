package com.example.pby.gam_study.widget.item;

import android.view.View;

public class ItemViewHolder {
    private final View mItemView;
    private int mPosition;

    public ItemViewHolder(View itemView) {
        this.mItemView = itemView;
    }

    void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }

    public View getItemView() {
        return mItemView;
    }
}
