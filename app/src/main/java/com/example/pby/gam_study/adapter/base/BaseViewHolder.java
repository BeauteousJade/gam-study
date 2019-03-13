package com.example.pby.gam_study.adapter.base;


import android.view.View;

import com.example.pby.gam_study.mvp.Presenter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public Presenter mPresenter;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public BaseViewHolder(@NonNull View itemView, Presenter presenter) {
        this(itemView);
        mPresenter = presenter;
    }
}
