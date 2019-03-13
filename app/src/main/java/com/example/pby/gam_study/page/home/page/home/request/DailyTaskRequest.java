package com.example.pby.gam_study.page.home.page.home.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.DailyTask;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.util.List;

import io.reactivex.Observable;

public class DailyTaskRequest extends BaseRequest<List<DailyTask>> {

    @Override
    public Observable<List<DailyTask>> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .findDailyCard(LoginManager.getCurrentUser().getId());
    }
}
