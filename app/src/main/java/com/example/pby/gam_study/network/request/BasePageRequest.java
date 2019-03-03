package com.example.pby.gam_study.network.request;

import com.example.pby.gam_study.network.response.Response;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BasePageRequest<T> implements Request<T> {

    private Disposable mDisposable;

    @Override
    public void enqueue(RequestCallback<T> requestCallback) {
        createObservable().subscribe(new Observer<T>() {
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
        onCreateObservable(start, end).subscribe(new Observer<T>() {
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
    public void cancel() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
