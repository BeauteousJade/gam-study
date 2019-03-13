package com.example.pby.gam_study.page.editCard.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.network.request.BaseRequest;
import com.example.pby.gam_study.util.GsonUtil;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class EditCardRequest extends BaseRequest<Card> {
    private Card mCard;
    private File mNewEditFile;

    public EditCardRequest(Card card) {
        mCard = card;
        mNewEditFile = new File(mCard.getEditImageUrl());
    }

    @Override
    public Observable<Card> createObservable() {
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("card", null, RequestBody.create(MediaType.parse("application/json"), GsonUtil.toString(mCard)))
                .addFormDataPart("file", mNewEditFile.getName(), RequestBody.create(MediaType.parse("image/*"), mNewEditFile))
                .build();
        return NetWorkManager
                .getService(Service.class)
                .editCard(body);
    }
}
