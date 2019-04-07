package com.example.pby.gam_study.page.home.page.home.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class CommitDailyTaskRequest extends BaseRequest<Boolean> {

    private String mUserId;

    public CommitDailyTaskRequest(String userId) {
        mUserId = userId;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager.getService(Service.class).commitDailyTask(mUserId);
    }
}
