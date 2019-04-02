package com.example.pby.gam_study.page.home.page.message.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class ResetUnReadCountRequest extends BaseRequest<Boolean> {

    private String mId;
    private boolean mIsFromUser;

    public ResetUnReadCountRequest(String id, boolean isFromUser) {
        mId = id;
        mIsFromUser = isFromUser;
    }

    @Override
    public Observable<Boolean> createObservable() {
        Service service = NetWorkManager.getService(Service.class);
        return mIsFromUser ? service.resetFromUserUnReadCount(mId) : service.resetToUserUnReadCount(mId);
    }
}
