package com.example.pby.gam_study.page.newKind.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class NewKindRequest extends BaseRequest<Integer> {

    private String mUserId;
    private String mKindName;
    private String mCover;

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public void setKindName(String kindName) {
        mKindName = kindName;
    }

    public void setCover(String cover) {
        mCover = cover;
    }

    @Override
    public Observable<Integer> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .insertKind(mUserId, mKindName, mCover);
    }
}
