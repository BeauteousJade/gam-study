package com.example.pby.gam_study.page.userlist.presenter.adapter;

import android.view.View;
import android.widget.Button;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;

import butterknife.BindView;

public class FollowButtonPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    User mUser;

    @BindView(R.id.follow)
    Button mFollowButton;

    @Override
    protected void onBind() {
        mFollowButton.setVisibility(View.VISIBLE);
        if (mUser.getIsFollow() == 1) {
            mFollowButton.setSelected(true);
            mFollowButton.setText("取消关注");
        } else {
            mFollowButton.setSelected(false);
            mFollowButton.setText("关注");
        }
    }
}
