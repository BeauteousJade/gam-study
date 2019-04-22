package com.example.pby.gam_study.page.profile.presenter;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.network.response.body.UserProfileResponseBody;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class UserProfileRefreshPresenter extends Presenter {
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Inject(AccessIds.REQUEST)
    Request mRequest;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter<Object> mAdapter;

    private final SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @SuppressWarnings("unchecked")
        @Override
        public void onRefresh() {
            mRequest.cancel();
            mRequest.enqueue(mRequestCallback);
        }
    };
    private final RequestCallback mRequestCallback = new RequestCallback() {
        @SuppressWarnings("unchecked")
        @Override
        public void onResult(Response response) {
            if (response.getError() == null && response.getData() != null) {
                UserProfileResponseBody data = (UserProfileResponseBody) response.getData();
                List<Object> dataList = new ArrayList<>();
                dataList.add(data.getUser());
                dataList.addAll(data.getPostList());
                mAdapter.setItemList(dataList);
            } else {
                mAdapter.setItemList(null);
            }
            if (mRefreshLayout != null) {
                mRefreshLayout.setRefreshing(false);
            }
        }
    };

    @SuppressWarnings("unchecked")
    @Override
    protected void onBind() {
        mRefreshLayout.setColorSchemeColors(ResourcesUtil.getColor(getCurrentActivity(), R.color.color_main_blue));
        mRefreshLayout.setProgressBackgroundColorSchemeColor(ResourcesUtil.getColor(getCurrentActivity(), R.color.white));
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setRefreshing(true);
        mRequest.enqueue(mRequestCallback);
    }
}
