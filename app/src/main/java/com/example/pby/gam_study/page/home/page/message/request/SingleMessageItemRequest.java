package com.example.pby.gam_study.page.home.page.message.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.MessageItem;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class SingleMessageItemRequest extends BaseRequest<MessageItem> {

    private String mFromUserId;
    private String mToUserId;

    public SingleMessageItemRequest(String fromUserId, String toUserId) {
        mFromUserId = fromUserId;
        mToUserId = toUserId;
    }

    @Override
    public Observable<MessageItem> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .findSingleMessageItem(mFromUserId, mToUserId);
    }
}
