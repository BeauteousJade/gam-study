package com.example.pby.gam_study.adapter.page;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.adapter.base.BaseViewHolder;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.fragment.tabhost.PageFragment;
import com.example.pby.gam_study.mvp.Presenter;

import java.util.List;

public abstract class PageFragmentAdapter extends BaseRecyclerAdapter<BaseFragment> {

    public PageFragmentAdapter(List<BaseFragment> dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        final View itemView = baseViewHolder.itemView;
        itemView.setId(generateId(itemView.getId(), position));
        super.onBindViewHolder(baseViewHolder, position);
    }

    protected int generateId(int old, int position) {
        return old;
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_fragment;
    }

    @Override
    protected Presenter onCreatePresenter(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new PageFragmentPresenter());
        return presenter;
    }
}
