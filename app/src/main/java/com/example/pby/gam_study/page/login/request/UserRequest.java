package com.example.pby.gam_study.page.login.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class UserRequest extends BaseRequest<User> {

    private String mId;

    public void setId(String id) {
        this.mId = id;
    }

    @Override
    public Observable<User> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .findUser(mId);
    }
}
