package com.example.pby.gam_study.page.home.page;

import android.support.annotation.DrawableRes;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.tabhost.PageFragment;
import com.example.pby.gam_study.widget.TitleBar;

import butterknife.BindView;

public abstract class BaseHomePageFragment extends PageFragment {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    @Override
    public void onPrepareBaseContext() {
    }

    public abstract String getTitle();


    @DrawableRes
    protected int getLeftIcon() {
        return 0;
    }

    @DrawableRes
    protected int getRightIcon() {
        return 0;
    }

    @Override
    public void onPageSelect() {
        mTitleBar.setTitle(getTitle());
        mTitleBar.setLeftIcon(getLeftIcon());
        mTitleBar.setRightIcon(getRightIcon());
    }

    @Override
    public void onPageUnSelect() {

    }
}
