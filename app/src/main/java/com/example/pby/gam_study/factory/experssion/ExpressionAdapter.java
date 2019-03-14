package com.example.pby.gam_study.factory.experssion;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;

import java.util.List;

public class ExpressionAdapter extends BaseRecyclerAdapter<String> {

    public ExpressionAdapter(List<String> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return 0;
    }

    @Override
    protected Presenter onCreatePresenter(int viewType) {
        return null;
    }
}
