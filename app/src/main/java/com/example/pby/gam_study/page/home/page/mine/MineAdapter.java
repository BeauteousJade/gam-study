package com.example.pby.gam_study.page.home.page.mine;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.home.page.mine.presenter.adapter.MineItemClickPresenter;
import com.example.pby.gam_study.page.home.page.mine.presenter.adapter.MineItemPresenter;
import com.example.pby.gam_study.page.home.page.mine.presenter.adapter.UserFollowClickPresenter;
import com.example.pby.gam_study.page.home.page.mine.presenter.adapter.UserHeadClickPresenter;
import com.example.pby.gam_study.page.home.page.mine.presenter.adapter.UserInfoPresenter;
import com.example.pby.gam_study.page.home.page.mine.presenter.adapter.UserNamePresenter;

import java.util.List;

public class MineAdapter extends BaseRecyclerAdapter<Object> {

    private static final int TYPE_INFO = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_EMPTY = 3;

    public MineAdapter(List<Object> dataList) {
        super(dataList);
    }


    @Override
    public int getItemViewTypeNoEmpty(int position) {
        if (position == 0) {
            return TYPE_INFO;
        } else if (mDataList.get(position) != null) {
            return TYPE_ITEM;
        } else {
            return TYPE_EMPTY;
        }
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        switch (viewType) {
            case TYPE_INFO:
                return R.layout.item_mine_user;
            case TYPE_ITEM:
                return R.layout.item_mine_item;
            default:
                return R.layout.item_mine_empty;
        }
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        switch (viewType) {
            case TYPE_INFO:
                presenter.add(new UserInfoPresenter());
                presenter.add(new UserHeadClickPresenter());
                presenter.add(new UserFollowClickPresenter());
                presenter.add(new UserNamePresenter());
                break;
            case TYPE_ITEM:
                presenter.add(new MineItemPresenter());
                presenter.add(new MineItemClickPresenter());
                break;
        }
        return presenter;
    }
}
