package com.example.pby.gam_study.page.profile.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class UnFollowRequest extends BaseRequest<Boolean> {

    private String mToUserId;

    public UnFollowRequest(String toUserId) {
        mToUserId = toUserId;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager.getService(Service.class).unFollowUser(LoginManager.getCurrentUser().getId(), mToUserId);
    }
}
