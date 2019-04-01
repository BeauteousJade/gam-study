package com.example.pby.gam_study.page.chat.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.util.List;

import io.reactivex.Observable;

public class ChatRequest extends BaseRequest<List<Message>> {

    private String mFromUserId;

    public ChatRequest(String fromUserId) {
        mFromUserId = fromUserId;
    }

    @Override
    public Observable<List<Message>> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .findHistoryMessage(mFromUserId, LoginManager.getCurrentUser().getId());
    }
}
