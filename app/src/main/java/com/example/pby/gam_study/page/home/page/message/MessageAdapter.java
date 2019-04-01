package com.example.pby.gam_study.page.home.page.message;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.MessageItem;
import com.example.pby.gam_study.page.home.page.message.presenter.MessageItemClickPresenter;
import com.example.pby.gam_study.page.home.page.message.presenter.MessageItemPresenter;

import java.util.List;

public class MessageAdapter extends BaseRecyclerAdapter<MessageItem> {

    public MessageAdapter(List<MessageItem> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_message;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new MessageItemPresenter());
        presenter.add(new MessageItemClickPresenter());
        return presenter;
    }
}
