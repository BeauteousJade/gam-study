package com.example.pby.gam_study.page.about;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class CheckUpdateRequest extends BaseRequest<String> {

    private String mCode;

    public CheckUpdateRequest(String code) {
        mCode = code;
    }

    @Override
    public Observable<String> createObservable() {
        return NetWorkManager.getService(Service.class).checkUpdate(mCode);
    }
}
