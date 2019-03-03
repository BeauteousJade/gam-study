package com.example.pby.gam_study.page.home.page.home.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Kind;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.util.List;

import io.reactivex.Observable;

public class RecentBrowserKindRequest extends BaseRequest<List<Kind>> {
    @Override
    public Observable<List<Kind>> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .findAllKind(LoginManager.getCurrentUser().getId());
    }
}
