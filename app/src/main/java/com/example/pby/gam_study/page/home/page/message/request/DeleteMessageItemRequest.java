package com.example.pby.gam_study.page.home.page.message.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class DeleteMessageItemRequest extends BaseRequest {

    private boolean mIsFromUser;
    private String mId;

    public DeleteMessageItemRequest(boolean isFromUser, String id) {
        mIsFromUser = isFromUser;
        mId = id;
    }

    @Override
    public Observable createObservable() {
        Service service = NetWorkManager.getService(Service.class);
        return mIsFromUser ? service.deleteMessageItemForFromUser(mId) : service.deleteMessageItemForToUser(mId);
    }
}
