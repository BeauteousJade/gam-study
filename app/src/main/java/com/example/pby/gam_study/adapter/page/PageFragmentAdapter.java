package com.example.pby.gam_study.adapter.page;

import com.example.pby.gam_study.fragment.BaseFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageFragmentAdapter extends FragmentStateAdapter {

    private List<BaseFragment> mFragmentList;

    public PageFragmentAdapter(FragmentManager fragmentManager, List<BaseFragment> fragmentsList) {
        super(fragmentManager);
        mFragmentList = fragmentsList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
