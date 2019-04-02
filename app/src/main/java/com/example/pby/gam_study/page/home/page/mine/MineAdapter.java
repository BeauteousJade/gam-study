package com.example.pby.gam_study.page.home.page.mine;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;

import java.util.List;

public class MineAdapter extends BaseRecyclerAdapter<String> {

    public MineAdapter(List<String> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return 0;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        return null;
    }
}
