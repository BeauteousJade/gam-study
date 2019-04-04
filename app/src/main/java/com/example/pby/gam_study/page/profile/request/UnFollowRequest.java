package com.example.pby.gam_study.page.profile.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class UnFollowRequest extends BaseRequest<Boolean> {

    private String mToUserId;
    private String mFromUserId;

    public UnFollowRequest(String fromUerId, String toUserId) {
        mFromUserId = fromUerId;
        mToUserId = toUserId;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager.getService(Service.class).unFollowUser(mFromUserId, mToUserId);
    }
}
