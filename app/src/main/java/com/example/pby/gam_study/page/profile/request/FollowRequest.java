package com.example.pby.gam_study.page.profile.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class FollowRequest extends BaseRequest<Boolean> {

    private String mToUserId;
    private String mFromUserId;

    public FollowRequest(String fromUserId, String toUserId) {
        mFromUserId = fromUserId;
        mToUserId = toUserId;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager.getService(Service.class).followUser(mFromUserId, mToUserId);
    }
}
