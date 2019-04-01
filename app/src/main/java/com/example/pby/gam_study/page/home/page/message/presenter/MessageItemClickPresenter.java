package com.example.pby.gam_study.page.home.page.message.presenter;

import android.view.View;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.MessageItem;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.chat.ChatActivity;

import java.util.Objects;

import butterknife.OnClick;

public class MessageItemClickPresenter extends Presenter {
    @Inject(AccessIds.ITEM_DATA)
    MessageItem mMessageItem;

    @OnClick(R.id.message_item_container)
    public void onMessageItemClick(View view) {
        final User otherUser = Objects.equals(LoginManager.getCurrentUser().getId(), mMessageItem.getFromUser().getId()) ? mMessageItem.getToUser() : mMessageItem.getFromUser();
        ChatActivity.startActivity(getCurrentActivity(), otherUser);
    }
}
