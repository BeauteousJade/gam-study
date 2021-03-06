package com.example.pby.gam_study.page.home.page.message.presenter;

import android.app.PendingIntent;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.MessageItem;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.chat.ChatActivity;
import com.example.pby.gam_study.page.home.page.message.MessageAdapter;
import com.example.pby.gam_study.page.home.page.message.request.SingleMessageItemRequest;
import com.example.pby.gam_study.page.setting.SettingFragment;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.GamNotificationManager;
import com.example.pby.gam_study.util.SharedPreferencesUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;
import java.util.Objects;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

public class MessageObserverPresenter extends Presenter {


    @Inject(AccessIds.RECYCLER_ADAPTER)
    MessageAdapter mAdapter;

    private PendingIntent mPendingIntent;

    private final Observer<List<IMMessage>> mIncomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    final IMMessage imMessage = messages.get(messages.size() - 1);
                    int index;
                    if ((index = isContainUser(imMessage.getFromAccount())) != -1) {
                        MessageItem messageItem = mAdapter.getItem(index);
                        boolean isFromUser = Objects.equals(messageItem.getFromUser().getId().toLowerCase(), imMessage.getFromAccount());
                        if (isFromUser) {
                            messageItem.setToUserUnReadCount(messageItem.getToUserUnReadCount() + messages.size());
                        } else {
                            messageItem.setFromUserUnReadCount(messageItem.getFromUserUnReadCount() + messages.size());
                        }
                        messageItem.setRecentContent(imMessage.getContent());
                        messageItem.setRecentTime(imMessage.getTime());
                        mAdapter.notifyItemChanged(index, MessageItem.COUNT + MessageItem.CONTENT + MessageItem.TIME);
                        showNotification(messageItem);
                    } else {
                        mRequest = new SingleMessageItemRequest(imMessage.getFromAccount(), LoginManager.getCurrentUser().getId());
                        mRequest.enqueue(mRequestCallback);
                    }
                    if (SharedPreferencesUtil.getBoolean(getCurrentActivity(), SettingFragment.RINGTONE, false)) {
                        DisplayUtil.playDefaultMediaPlayer(getCurrentActivity());
                    }
                }
            };

    private final RequestCallback<MessageItem> mRequestCallback = new RequestCallback<MessageItem>() {
        @Override
        public void onResult(Response<MessageItem> response) {
            if (response.getError() == null && response.getData() != null) {
                MessageItem messageItem = response.getData();
                mAdapter.addItem(0, messageItem);
                showNotification(messageItem);
            }
        }
    };
    private Request<MessageItem> mRequest;

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

    private void showNotification(MessageItem messageItem) {
        if (SharedPreferencesUtil.getBoolean(getCurrentActivity(), SettingFragment.NOTIFICATION, false)) {
            mPendingIntent = PendingIntent.getActivity(getCurrentActivity(), 0,
                    ChatActivity.getStartActivityIntent(getCurrentActivity(), messageItem.getToUser()), FLAG_CANCEL_CURRENT);
            GamNotificationManager.getInstance(getCurrentActivity()).setMessageNotification(mPendingIntent);
        }
    }

    private int isContainUser(String userId) {
        final List<MessageItem> messageList = mAdapter.getDataList();
        for (int i = 0; i < messageList.size(); i++) {
            if (messageList.get(i) == null) {
                continue;
            }
            if (Objects.equals(messageList.get(i).getFromUser().getId().toLowerCase(), userId) ||
                    Objects.equals(messageList.get(i).getToUser().getId().toLowerCase(), userId)) {
                return i;
            }
        }
        return -1;
    }
}
