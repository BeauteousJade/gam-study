package com.example.pby.gam_study.page.post.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Comment;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class CommentRequest extends BaseRequest<Comment> {
    private Comment mComment;

    public CommentRequest(Comment comment) {
        mComment = comment;
    }

    @Override
    public Observable<Comment> createObservable() {
        return NetWorkManager.getService(Service.class).addComment(mComment.toString());
    }
}
