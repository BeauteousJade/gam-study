package com.example.pby.gam_study.page.home.page.message;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;

import java.util.List;

public class MessageAdapter extends BaseRecyclerAdapter<String> {
    public MessageAdapter(List<String> dataList) {
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
