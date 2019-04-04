package com.example.pby.gam_study.page.userlist.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.util.List;

import io.reactivex.Observable;

public class FansListRequest extends BaseRequest<List<User>> {

    private String mUseId;

    public FansListRequest(String userId) {
        mUseId = userId;
    }

    @Override
    public Observable<List<User>> createObservable() {
        return NetWorkManager.getService(Service.class).findFansList(mUseId);
    }
}
