package com.example.pby.gam_study.page.home.page.home.adapter;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Kind;
import com.example.pby.gam_study.page.home.page.home.presenter.KindPresenter;

import java.util.List;

public class KindAdapter extends BaseRecyclerAdapter<Kind> {


    public KindAdapter(List<Kind> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_recent_browser;
    }

    @Override
    protected Presenter onCreatePresenter(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new KindPresenter());
        return presenter;
    }
}
