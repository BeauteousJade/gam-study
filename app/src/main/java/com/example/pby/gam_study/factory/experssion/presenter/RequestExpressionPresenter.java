package com.example.pby.gam_study.factory.experssion.presenter;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.experssion.ExpressionRequest;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;

import java.util.List;

public class RequestExpressionPresenter extends Presenter {

    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter<String> mAdapter;

    private Request<List<String>> mRequest;


    private final RequestCallback<List<String>> mRequestCallback = new RequestCallback<List<String>>() {
        @Override
        public void onResult(Response<List<String>> response) {
            if (response.getError() == null && response.getData() != null) {
                mAdapter.setItemList(response.getData());
            }
        }
    };


    @Override
    protected void onCreate() {
        mRequest = new ExpressionRequest(getCurrentActivity());
    }

    @Override
    protected void onBind() {
        mRequest.cancel();
        mRequest.enqueue(mRequestCallback);
    }

    @Override
    protected void onUnBind() {
        mRequest.cancel();
    }

    @Override
    protected void onDestroy() {
        mRequest.cancel();
    }
}
