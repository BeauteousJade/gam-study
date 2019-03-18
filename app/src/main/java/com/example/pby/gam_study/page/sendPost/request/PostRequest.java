package com.example.pby.gam_study.page.sendPost.request;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.request.BaseRequest;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.GsonUtil;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostRequest extends BaseRequest<Post> {

    private Post mPost;

    public PostRequest(Post post) {
        mPost = post;
    }

    @Override
    public Observable<Post> createObservable() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("post", null, RequestBody.create(MediaType.parse("application/json"), GsonUtil.toString(mPost)));
        if (!ArrayUtil.isEmpty(mPost.getImageUrlList())) {
            final List<String> imageUrlList = mPost.getImageUrlList();
            for (String url : imageUrlList) {
                File file = new File(url);
                builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        return NetWorkManager
                .getService(Service.class)
                .insertPost(builder.build());
    }
}
