package com.example.pby.gam_study.page.home.page.mine.request;

import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.BaseRequest;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateAvatarRequest extends BaseRequest<String> {

    private File mAvatarFile;

    public UpdateAvatarRequest(String avatarPath) {
        mAvatarFile = new File(avatarPath);
    }

    @Override
    public Observable<String> createObservable() {
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId", LoginManager.getCurrentUser().getId())
                .addFormDataPart("file", mAvatarFile.getName(), RequestBody.create(MediaType.parse("image/*"), mAvatarFile))
                .build();
        return NetWorkManager.getService(Service.class).updateAvatar(body);
    }
}
