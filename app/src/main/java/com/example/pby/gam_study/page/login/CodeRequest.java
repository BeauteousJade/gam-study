package com.example.pby.gam_study.page.login;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.RxSchedulers;
import com.example.pby.gam_study.network.Service;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.login.bean.CodeBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CodeRequest implements Request<CodeBean> {

    private Disposable mDisposable;

    @Override
    public void enqueue(final RequestCallback<CodeBean> requestCallback) {
        NetWorkManager
                .getService(Service.class)
                .getCode()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new Observer<CodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(CodeBean codeBean) {
                        if (requestCallback != null) {
                            requestCallback.onResult(new Response<>(codeBean, null));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (requestCallback != null) {
                            requestCallback.onResult(new Response<CodeBean>(null, e));
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
