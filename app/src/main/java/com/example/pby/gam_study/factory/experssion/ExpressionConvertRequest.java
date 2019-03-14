package com.example.pby.gam_study.factory.experssion;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import com.example.pby.gam_study.network.request.BaseRequest;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.WorkerThread;
import io.reactivex.Observable;

public class ExpressionConvertRequest extends BaseRequest<Drawable> {

    private String mFileName;
    private AssetManager mAssetManager;

    public ExpressionConvertRequest(Context context, String fileName) {
        mFileName = fileName;
        mAssetManager = context.getAssets();
    }


    @Override
    public Observable<Drawable> createObservable() {
        return Observable.create(emitter -> {
            if (!emitter.isDisposed()) {
                Drawable drawable = generateDrawable(mFileName);
                if (drawable != null) {
                    emitter.onNext(drawable);
                } else {
                    emitter.onError(new NullPointerException());
                    return;
                }
            }
            emitter.onComplete();
        });
    }

    @WorkerThread
    private Drawable generateDrawable(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = mAssetManager.open("expression/" + fileName);
            return Drawable.createFromStream(inputStream, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
