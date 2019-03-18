package com.example.pby.gam_study.page.post.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class LikeRequest extends BaseRequest<Boolean> {

    private String mPostId;
    private boolean mIsLike;

    public LikeRequest(String postId, boolean isLike) {
        mPostId = postId;
        mIsLike = isLike;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .like(LoginManager.getCurrentUser().getId(), mPostId, mIsLike);
    }
}
