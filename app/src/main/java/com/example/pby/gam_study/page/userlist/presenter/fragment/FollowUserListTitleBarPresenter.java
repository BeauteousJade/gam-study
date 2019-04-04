package com.example.pby.gam_study.page.userlist.presenter.fragment;

import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class FollowUserListTitleBarPresenter extends Presenter {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    @Override
    protected void onBind() {
        mTitleBar.setLeftIcon(R.drawable.bg_back);
        mTitleBar.setTitle(getString(R.string.title_follow_list));
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }
}
