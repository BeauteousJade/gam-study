package com.example.pby.gam_study.page.setting;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.setting.presenter.SettingItemPresenter;

import java.util.List;

public class SettingAdapter extends BaseRecyclerAdapter<Object> {

    public SettingAdapter(List<Object> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_setting;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new SettingItemPresenter());
        return presenter;
    }
}
