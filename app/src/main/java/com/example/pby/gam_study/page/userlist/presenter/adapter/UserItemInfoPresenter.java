package com.example.pby.gam_study.page.userlist.presenter.adapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.GlideFactory;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;

import butterknife.BindView;

public class UserItemInfoPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    User mUser;

    @BindView(R.id.avatar)
    ImageView mAvatarView;
    @BindView(R.id.name)
    TextView mNameView;
    @BindView(R.id.follow)
    Button mFollowButton;

    @Override
    protected void onBind() {
        GlideApp.with(getCurrentFragment()).asBitmap().apply(GlideFactory.createCircleOption()).load(mUser.getHead()).into(mAvatarView);
        mNameView.setText(mUser.getNickName());
        if (mUser.getIsFollow() == 1) {
            mFollowButton.setSelected(true);
            mFollowButton.setText("取消关注");
        } else {
            mFollowButton.setSelected(false);
            mFollowButton.setText("关注");
        }
    }
}
