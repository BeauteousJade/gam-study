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
    private long mStartTime;
    private long mEndTime;

    public ChatRequest(String fromUserId, long startTime, long endTime) {
        mFromUserId = fromUserId;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    public long getStartTime() {
        return mStartTime;
    }

    @Override
    public Observable<List<Message>> createObservable() {
        return NetWorkManager.getService(Service.class).findHistoryMessage(mFromUserId,
                LoginManager.getCurrentUser().getId(), mStartTime, mEndTime);
    }
}
