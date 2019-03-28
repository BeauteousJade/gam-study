package com.example.pby.gam_study.page.profile.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.util.List;

import io.reactivex.Observable;

public class UserPostRequest extends BaseRequest<List<Post>> {

    private String mUserId;

    public UserPostRequest(String userId) {
        mUserId = userId;
    }

    @Override
    public Observable<List<Post>> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .findPost(mUserId);
    }
}
