package com.example.pby.gam_study.page.modifyUserName;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class ModifyUserNameRequest extends BaseRequest<Boolean> {

    private String mUserId;
    private String mUserName;

    public ModifyUserNameRequest(String userId, String userName) {
        mUserId = userId;
        mUserName = userName;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .modifyUserName(mUserId, mUserName);
    }
}
