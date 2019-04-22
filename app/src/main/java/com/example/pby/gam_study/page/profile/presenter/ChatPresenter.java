package com.example.pby.gam_study.page.profile.presenter;

import android.view.View;
import android.widget.Button;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.chat.ChatActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    User mUser;

    @BindView(R.id.send_message)
    Button mSendMessageButton;

    @Override
    protected void onBind() {
        if (Objects.equals(mUser.getId(), LoginManager.getCurrentUser().getId())) {
            mSendMessageButton.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.send_message)
    public void onSendMessageClick(View view) {
        ChatActivity.startActivity(getCurrentActivity(), mUser);
    }
}
