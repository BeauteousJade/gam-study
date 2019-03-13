package com.example.pby.gam_study.page.dailyCard;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import io.reactivex.Observable;

public class UpdateDailyCardRequest extends BaseRequest<Boolean> {

    private String mCardId;

    public UpdateDailyCardRequest(String cardId) {
        mCardId = cardId;
    }


    @Override
    public Observable<Boolean> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .updateDailyCard(LoginManager.getCurrentUser().getId(), mCardId);
    }
}
