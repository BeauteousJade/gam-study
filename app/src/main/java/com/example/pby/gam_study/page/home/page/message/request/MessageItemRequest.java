package com.example.pby.gam_study.page.home.page.message.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.MessageItem;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.util.List;

import io.reactivex.Observable;

public class MessageItemRequest extends BaseRequest<List<MessageItem>> {

    @Override
    public Observable<List<MessageItem>> createObservable() {

        return NetWorkManager
                .getService(Service.class)
                .findMessageItem(LoginManager.getCurrentUser().getId());
    }
}
