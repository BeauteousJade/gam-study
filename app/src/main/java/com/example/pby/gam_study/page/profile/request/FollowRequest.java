package com.example.pby.gam_study.page.profile.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class FollowRequest extends BaseRequest<Boolean> {

    private String mToUserId;

    public FollowRequest(String toUserId) {
        mToUserId = toUserId;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager.getService(Service.class).followUser(LoginManager.getCurrentUser().getId(), mToUserId);
    }
}