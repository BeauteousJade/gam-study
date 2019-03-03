package com.example.pby.gam_study.network.request;

import com.example.pby.gam_study.network.RxSchedulers;
import com.example.pby.gam_study.network.bean.Kind;
import com.example.pby.gam_study.network.response.Response;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseRequest<T> implements Request<T> {

    private Disposable mDisposable;

    @Override
    public void enqueue(RequestCallback<T> requestCallback) {
        createObservable()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(T t) {
                        if (requestCallback != null) {
                            requestCallback.onResult(new Response<>(t, null));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (requestCallback != null) {
                            requestCallback.onResult(new Response<>(null, e));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void enqueue(RequestCallback<T> requestCallback, int start, int end) {

    }

    @Override
    public void enqueue(int start, int end) {

    }


    @Override
    public Observable<T> onCreateObservable(int start, int end) {
        return null;
    }

    @Override
    public void cancel() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
