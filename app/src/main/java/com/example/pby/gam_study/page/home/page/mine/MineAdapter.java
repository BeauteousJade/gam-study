package com.example.pby.gam_study.page.home.page.mine;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.home.page.mine.presenter.adapter.UserInfoPresenter;

import java.util.List;

public class MineAdapter extends BaseRecyclerAdapter<Object> {

    private static final int TYPE_INFO = 1;
    private static final int TYPE_ITEM = 2;

    public MineAdapter(List<Object> dataList) {
        super(dataList);
    }


    @Override
    public int getItemViewTypeNoEmpty(int position) {
        if (position == 0) {
            return TYPE_INFO;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        switch (viewType) {
            case TYPE_INFO:
                return R.layout.item_mine_user;
            case TYPE_ITEM:
                return R.layout.item_mine_item;
        }
        return 0;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        switch (viewType) {
            case TYPE_INFO:
                presenter.add(new UserInfoPresenter());
                break;
            case TYPE_ITEM:
                presenter.add(null);
                break;
        }
        return presenter;
    }
}
