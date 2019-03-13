package com.example.pby.gam_study.page.home.page.home.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class SignRequest extends BaseRequest<Boolean> {

    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .sign(LoginManager.getCurrentUser().getId());
    }
}
