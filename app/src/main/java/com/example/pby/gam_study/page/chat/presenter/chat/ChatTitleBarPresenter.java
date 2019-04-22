package com.example.pby.gam_study.page.chat.presenter.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatTitleBarPresenter extends Presenter {

    @Inject(AccessIds.USER)
    User mUser;

    @BindView(R.id.left_icon)
    ImageView mLeftIcon;
    @BindView(R.id.title)
    TextView mTitleView;

    @Override
    protected void onBind() {
        mLeftIcon.setImageDrawable(getDrawable(R.drawable.bg_back));
        mTitleView.setText(mUser.getNickName());
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }
}
