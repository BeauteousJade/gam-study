package com.example.pby.gam_study.page.home.page.home.adapter;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.DailyTask;
import com.example.pby.gam_study.page.home.page.home.presenter.DailyTaskClickPresenter;
import com.example.pby.gam_study.page.home.page.home.presenter.DailyTaskPresenter;

import java.util.List;

public class DailyTaskAdapter extends BaseRecyclerAdapter<DailyTask> {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ITEM = 1;

    public DailyTaskAdapter(List<DailyTask> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewTypeNoEmpty(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemStablePosition() {
        return 0;
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        if (viewType == TYPE_TITLE) {
            return R.layout.item_daily_task_title;
        }
        return R.layout.item_daily_task_item;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        switch (viewType) {
            case TYPE_ITEM:
                presenter.add(new DailyTaskPresenter());
                presenter.add(new DailyTaskClickPresenter());
                break;
        }
        return presenter;
    }
}
