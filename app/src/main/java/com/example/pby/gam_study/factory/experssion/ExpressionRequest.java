package com.example.pby.gam_study.factory.experssion;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.pby.gam_study.network.request.BaseRequest;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class ExpressionRequest extends BaseRequest<List<String>> {

    private AssetManager mAssetManager;

    public ExpressionRequest(Context context) {
        mAssetManager = context.getAssets();
    }

    @Override
    public Observable<List<String>> createObservable() {
        return Observable.create(emitter -> {
            List<String> expressionList = Arrays.asList(mAssetManager.list("expression"));
            emitter.onNext(expressionList);
            emitter.onComplete();
        });
    }
}
