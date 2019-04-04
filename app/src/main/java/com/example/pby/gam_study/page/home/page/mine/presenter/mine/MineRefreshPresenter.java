package com.example.pby.gam_study.page.home.page.mine.presenter.mine;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.network.response.body.MineResponseBody;
import com.example.pby.gam_study.page.home.page.mine.request.MineRequest;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class MineRefreshPresenter extends Presenter {

    @Inject(AccessIds.REQUEST)
    MineRequest mRequest;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter mAdapter;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;


    private final SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @SuppressWarnings("unchecked")
        @Override
        public void onRefresh() {
            mRequest.cancel();
            mRequest.enqueue(mRequestCallback);
        }
    };

    private RequestCallback<MineResponseBody> mRequestCallback = new RequestCallback<MineResponseBody>() {

        @SuppressWarnings("unchecked")
        @Override
        public void onResult(Response<MineResponseBody> response) {
            if (response.getError() == null && response.getData() != null) {
                MineResponseBody responseData = response.getData();
                List list = new ArrayList();
                list.add(responseData.getUser());
                list.addAll(responseData.getDataList());
                mAdapter.setItemList(list);
            }
            mRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    protected void onBind() {
        mRefreshLayout.setColorSchemeColors(ResourcesUtil.getColor(getCurrentActivity(), R.color.color_main_blue));
        mRefreshLayout.setProgressBackgroundColorSchemeColor(ResourcesUtil.getColor(getCurrentActivity(), R.color.white));
        if (mRequest != null) {
            mRequest.cancel();
        }
        mRequest = new MineRequest();
        mRequest.enqueue(mRequestCallback);
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }
}
