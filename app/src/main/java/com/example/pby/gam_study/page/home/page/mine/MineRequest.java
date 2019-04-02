package com.example.pby.gam_study.page.home.page.mine;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;
import com.example.pby.gam_study.network.response.body.MineResponseBody;

import io.reactivex.Observable;

public class MineRequest extends BaseRequest<MineResponseBody> {

    @Override
    public Observable<MineResponseBody> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .findUserInfo(LoginManager.getCurrentUser().getId());
    }
}
