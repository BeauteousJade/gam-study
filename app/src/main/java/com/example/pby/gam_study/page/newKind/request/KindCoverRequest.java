package com.example.pby.gam_study.page.newKind.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;
import com.example.pby.gam_study.page.newKind.NewKindItem;

import java.util.List;

import io.reactivex.Observable;

public class KindCoverRequest extends BaseRequest<List<NewKindItem>> {

    @Override
    public Observable<List<NewKindItem>> createObservable() {
        return NetWorkManager.getService(Service.class).findKindCover();
    }
}
