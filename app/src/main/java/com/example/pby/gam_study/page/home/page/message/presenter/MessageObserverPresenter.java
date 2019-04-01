package com.example.pby.gam_study.page.home.page.message.presenter;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.MessageItem;
import com.example.pby.gam_study.page.home.page.message.MessageAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;
import java.util.Objects;

public class MessageObserverPresenter extends Presenter {


    @Inject(AccessIds.RECYCLER_ADAPTER)
    MessageAdapter mAdapter;

    private final Observer<List<IMMessage>> mIncomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    final IMMessage imMessage = messages.get(messages.size() - 1);
                    int index;
                    if ((index = isContainUser(imMessage.getFromAccount())) != -1) {
                        MessageItem messageItem = mAdapter.getItem(index);
                        if (Objects.equals(messageItem.getFromUser().getId().toLowerCase(), imMessage.getFromAccount())) {
                            messageItem.setToUserUnReadCount(messageItem.getToUserUnReadCount() + messages.size());
                        } else {
                            messageItem.setFromUserUnReadCount(messageItem.getFromUserUnReadCount() + messages.size());
                            mAdapter.notifyItemChanged(index);
                        }
                        messageItem.setRecentContent(imMessage.getContent());
                        mAdapter.notifyItemChanged(index);
                    } else {

                    }
                }
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

    private int isContainUser(String userId) {
        final List<MessageItem> messageList = mAdapter.getDataList();
        for (int i = 0; i < messageList.size(); i++) {
            if (Objects.equals(messageList.get(i).getFromUser().getId().toLowerCase(), userId) ||
                    Objects.equals(messageList.get(i).getToUser().getId().toLowerCase(), userId)) {
                return i;
            }
        }
        return -1;
    }
}
