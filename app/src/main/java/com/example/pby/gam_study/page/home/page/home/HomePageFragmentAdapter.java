package com.example.pby.gam_study.page.home.page.home;


import com.example.pby.gam_study.adapter.page.PageFragmentAdapter;
import com.example.pby.gam_study.fragment.BaseFragment;

import java.util.List;

public class HomePageFragmentAdapter extends PageFragmentAdapter {
    public HomePageFragmentAdapter(List<BaseFragment> dataList) {
        super(dataList);
    }


    @Override
    protected int generateId(int old, int position) {
        return old + position;
    }
}
