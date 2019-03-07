package com.example.pby.gam_study.page.newCard;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NewCardRequest extends BaseRequest<Card> {

    private String mKindId;
    private String mAnswer;
    private File mOldImageFile;
    private File mEditImageFile;

    public NewCardRequest(String kindId, String oldImageUrl, String editImageUrl, String answer) {
        mKindId = kindId;
        mAnswer = answer;
        mOldImageFile = new File(oldImageUrl);
        mEditImageFile = new File(editImageUrl);
    }


    @Override
    public Observable<Card> createObservable() {
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId", LoginManager.getCurrentUser().getId())
                .addFormDataPart("kindId", mKindId)
                .addFormDataPart("answer", mAnswer)
                .addFormDataPart("file", mOldImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), mOldImageFile))
                .addFormDataPart("file", mEditImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), mEditImageFile))
                .build();
        return NetWorkManager
                .getService(Service.class)
                .insertCard(body);
    }
}
