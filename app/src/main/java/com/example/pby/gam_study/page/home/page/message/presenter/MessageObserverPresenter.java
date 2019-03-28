package com.example.pby.gam_study.page.home.page.message.presenter;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Message;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;
import java.util.Objects;

public class MessageObserverPresenter extends Presenter {


    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter<Message> mAdapter;

    private final Observer<List<IMMessage>> mIncomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    final IMMessage imMessage = messages.get(messages.size() - 1);
                    int index = -1;
                    if ((index = isContainUser(imMessage.getFromAccount())) != -1) {
                        final Message message = mAdapter.getItem(index);
                        message.setTime(imMessage.getTime());
                        message.setNewestContent(imMessage.getContent());
                        message.setUnReadCount(message.getUnReadCount() + messages.size());
                        mAdapter.notifyItemChanged(index);
                    } else {
                        final Message message = new Message();
                        message.setFromUserId(imMessage.getFromAccount());
                        message.setUnReadCount(messages.size());
                        message.setNewestContent(imMessage.getContent());
                        message.setTime(imMessage.getTime());
                        mAdapter.addItem(0, message);
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
        final List<Message> messageList = mAdapter.getDataList();
        for (int i = 0; i < messageList.size(); i++) {
            if (Objects.equals(messageList.get(i).getFromUserId(), userId)) {
                return i;
            }
        }
        return -1;
    }
}
