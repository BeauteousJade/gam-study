package com.example.pby.gam_study.page.home.page.home.item;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.tabhost.PageFragment;

public class DailyTaskFragment extends PageFragment {

    public static DailyTaskFragment newInstance() {
        return new DailyTaskFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily_task;
    }

    @Override
    public void onPageSelect() {

    }

    @Override
    public void onPageUnSelect() {

    }
}
