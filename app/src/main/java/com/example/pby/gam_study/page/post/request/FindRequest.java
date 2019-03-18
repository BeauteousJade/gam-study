package com.example.pby.gam_study.page.post.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.util.List;

import io.reactivex.Observable;

public class FindRequest extends BaseRequest<List<Post>> {
    @Override
    public Observable<List<Post>> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .findRecommendPost();
    }
}
