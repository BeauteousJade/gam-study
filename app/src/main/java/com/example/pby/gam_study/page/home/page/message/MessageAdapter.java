package com.example.pby.gam_study.page.home.page.message;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.network.bean.MessageItem;

import java.util.List;

public class MessageAdapter extends BaseRecyclerAdapter<MessageItem> {

    public MessageAdapter(List<MessageItem> dataList) {
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
