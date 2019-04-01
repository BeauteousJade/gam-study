package com.example.pby.gam_study.page.chat.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.network.request.BaseRequest;
import com.example.pby.gam_study.util.GsonUtil;

import io.reactivex.Observable;
import okhttp3.FormBody;

public class SendMessageRequest extends BaseRequest<Boolean> {

    private Message mMessage;

    public SendMessageRequest(Message message) {
        mMessage = message;
    }

    @Override
    public Observable<Boolean> createObservable() {
        FormBody formBody = new FormBody.Builder()
                .add("message", GsonUtil.toString(mMessage))
                .build();
        return NetWorkManager
                .getService(Service.class)
                .sendMessage(formBody);
    }
}
