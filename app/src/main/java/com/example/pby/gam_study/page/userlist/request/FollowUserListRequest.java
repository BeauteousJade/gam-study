package com.example.pby.gam_study.page.userlist.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class FollowUserListRequest extends BaseRequest {

    private String mUserId;

    public FollowUserListRequest(String userId) {
        mUserId = userId;
    }

    @Override
    public Observable createObservable() {
        return NetWorkManager.getService(Service.class)
                .findFollowList(mUserId);
    }
}
