package com.example.pby.gam_study.adapter.page;

import android.view.View;

import com.example.pby.gam_study.adapter.base.BaseViewHolder;
import com.example.pby.gam_study.fragment.tabhost.PageFragment;

import androidx.annotation.NonNull;

public class PageFragmentViewHolder extends BaseViewHolder {

    public PageFragment mPageFragment;

    public PageFragmentViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public PageFragmentViewHolder(@NonNull View itemView, PageFragment pageFragment) {
        this(itemView);
        mPageFragment = pageFragment;
    }
}
