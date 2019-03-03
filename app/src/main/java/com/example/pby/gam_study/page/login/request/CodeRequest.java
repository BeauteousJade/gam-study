package com.example.pby.gam_study.page.login.request;

import com.example.pby.gam_study.network.request.BaseRequest;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.page.login.bean.CodeBean;

import io.reactivex.Observable;

public class CodeRequest extends BaseRequest<CodeBean> {
    @Override
    public Observable<CodeBean> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .getCode();
    }
}
