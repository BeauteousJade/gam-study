package com.example.pby.gam_study.page.chat.presenter.chat;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.chat.ChatAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

public class NewMessagePresenter extends Presenter {

    @Inject(AccessIds.USER)
    User mUser;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    ChatAdapter mAdapter;
    @Inject(AccessIds.RECYCLER_VIEW)
    RecyclerView mRecyclerView;

    private final Observer<List<IMMessage>> mIncomingMessageObserver =
            (Observer<List<IMMessage>>) messages -> {
                List<Message> messageList = new ArrayList<>();
                for (IMMessage imMessage : messages) {
                    if (Objects.equals(LoginManager.getCurrentUser().getId().toLowerCase(), imMessage.getFromAccount())) {
                        return;
                    }
                    Message message = new Message();
                    message.setId(imMessage.getUuid());
                    message.setContent(imMessage.getContent());
                    message.setFromUser(mUser);
                    message.setToUser(LoginManager.getCurrentUser());
                    messageList.add(message);
                }
                mAdapter.addItemList(messageList);
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
            };

    @Override
    protected void onCreate() {
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(mIncomingMessageObserver, true);
    }

    @Override
    protected void onDestroy() {
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(mIncomingMessageObserver, false);
    }
}