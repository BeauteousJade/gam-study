package com.example.pby.gam_study.page.card.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class UpdateTimeRequest extends BaseRequest<Boolean> {

    private String mKindId;

    public UpdateTimeRequest(String kindId) {
        mKindId = kindId;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager.getService(Service.class).updateTime(mKindId);
    }
}
