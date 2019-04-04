package com.example.pby.gam_study.page.userlist.adapter;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.userlist.presenter.adapter.FollowUserItemClickPresenter;
import com.example.pby.gam_study.page.userlist.presenter.adapter.UserItemInfoPresenter;

import java.util.List;

public class FollowUserListAdapter extends BaseRecyclerAdapter<User> {

    public FollowUserListAdapter(List<User> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_user_list;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new UserItemInfoPresenter());
        presenter.add(new FollowUserItemClickPresenter());
        return presenter;
    }
}
