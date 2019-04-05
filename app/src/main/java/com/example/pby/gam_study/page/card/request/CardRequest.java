package com.example.pby.gam_study.page.card.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.util.List;

import io.reactivex.Observable;

/**
 * 获取某个分类的所有卡片
 * {@link CardRequest#mKindId}可以为null，如果为null，表示获取该用户的所有卡片
 */
public class CardRequest extends BaseRequest<List<Card>> {

    private String mKindId;

    public CardRequest(String kindId) {
        mKindId = kindId;
    }

    @Override
    public Observable<List<Card>> createObservable() {
        return NetWorkManager
                .getService(Service.class)
                .findAllCard(mKindId, LoginManager.getCurrentUser().getId());
    }
}
