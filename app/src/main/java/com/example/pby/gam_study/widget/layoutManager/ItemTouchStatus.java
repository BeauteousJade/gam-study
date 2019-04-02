package com.example.pby.gam_study.widget.layoutManager;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchStatus {
    boolean onItemRemove(int position);

    boolean isShowEmpty();

    void onSaveItemStatus(RecyclerView.ViewHolder viewHolder);
}
