package com.example.pby.gam_study.page.profile.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;
import com.example.pby.gam_study.network.response.body.UserProfileResponseBody;

import io.reactivex.Observable;

public class UserProfileRequest extends BaseRequest<UserProfileResponseBody> {

    private String mUserId;

    public UserProfileRequest(String userId) {
        mUserId = userId;
    }

    @Override
    public Observable<UserProfileResponseBody> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .getUserProfile(mUserId);
    }
}
