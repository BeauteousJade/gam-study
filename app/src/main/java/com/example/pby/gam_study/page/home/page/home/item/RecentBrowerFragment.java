package com.example.pby.gam_study.page.home.page.home.item;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.tabhost.PageFragment;

public class RecentBrowerFragment extends PageFragment {

    public static RecentBrowerFragment newInstance() {
        return new RecentBrowerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recent_brower;
    }

    @Override
    public void onPageSelect() {

    }

    @Override
    public void onPageUnSelect() {

    }
}
