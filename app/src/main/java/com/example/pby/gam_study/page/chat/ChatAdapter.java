package com.example.pby.gam_study.page.chat;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.page.chat.presenter.adapter.FromUserMessagePresenter;
import com.example.pby.gam_study.page.chat.presenter.adapter.ToUserMessagePresenter;

import java.util.List;
import java.util.Objects;

public class ChatAdapter extends BaseRecyclerAdapter<Message> {

    private static final int OTHER_USER_TYPE = 1;
    private static final int MINE_TYPE = 2;

    public ChatAdapter(List<Message> dataList) {
        super(dataList);
    }

    @Override
    protected boolean supportEmpty() {
        return false;
    }

    @Override
    public int getItemViewTypeNoEmpty(int position) {
        if (Objects.equals(mDataList.get(position).getFromUser().getId(), LoginManager.getCurrentUser().getId())) {
            return MINE_TYPE;
        }
        return OTHER_USER_TYPE;
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        switch (viewType) {
            case MINE_TYPE:
                return R.layout.item_message_mine;
            case OTHER_USER_TYPE:
                return R.layout.item_message_other_user;
        }
        return 0;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        switch (viewType) {
            case OTHER_USER_TYPE:
                presenter.add(new FromUserMessagePresenter());
                break;
            case MINE_TYPE:
                presenter.add(new ToUserMessagePresenter());
                break;
        }
        return presenter;
    }
}
