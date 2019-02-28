package com.example.pby.gam_study.page.home.page.home.item;


import com.example.pby.gam_study.adapter.page.PageFragmentAdapter;
import com.example.pby.gam_study.fragment.tabhost.PageFragment;

import java.util.List;

public class HomePageFragmentAdapter extends PageFragmentAdapter {
    public HomePageFragmentAdapter(List<PageFragment> dataList) {
        super(dataList);
    }

    @Override
    protected int generateId(int old, int position) {
        return old + position;
    }
}
