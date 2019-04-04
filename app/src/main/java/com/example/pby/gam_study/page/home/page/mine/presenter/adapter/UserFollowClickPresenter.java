package com.example.pby.gam_study.page.home.page.mine.presenter.adapter;

import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.userlist.UserListActivity;

import butterknife.OnClick;

public class UserFollowClickPresenter extends Presenter {

    @OnClick(R.id.follow_count)
    public void onFollowClick(View view) {
        UserListActivity.startActivity(getCurrentActivity(), UserListActivity.ListType.TYPE_FOLLOW);
    }

    @OnClick(R.id.fans_count)
    public void onFansClick(View view) {
        UserListActivity.startActivity(getCurrentActivity(), UserListActivity.ListType.TYPE_FANS);
    }
}
