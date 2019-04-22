package com.example.pby.gam_study.fragment;


import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class RefreshPresenter extends Presenter {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Inject(AccessIds.REQUEST)
    Request mRequest;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter mAdapter;
    @Inject(AccessIds.FRAGMENT)
    RefreshRecyclerViewFragment mFragment;

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
            if (response.getError() == null) {
                if (mFragment.canLoadMore()) {
                    final int indexBesideLoadMoreView = mAdapter.getItemCount();
                    if (indexBesideLoadMoreView >= 0) {
                        mAdapter.addItemList(indexBesideLoadMoreView, (List) response.getData());
                    } else {
                        mAdapter.addItemList((List) response.getData());
                    }
                } else {
                    mAdapter.setItemList((List) response.getData());
                }
            } else {
                mAdapter.setItemList(null);
            }
            if (mRefreshLayout != null) {
                mRefreshLayout.setRefreshing(false);
            }
        }
    };

    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @SuppressWarnings("unchecked")
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                final int lastPosition;
                if (layoutManager instanceof LinearLayoutManager) {
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    int[] positions = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
                    int max = 0;
                    for (int i = 0; i < positions.length; i++) {
                        max = Math.max(max, positions[i]);
                    }
                    lastPosition = max;
                } else {
                    lastPosition = 0;
                }
                if (lastPosition == mAdapter.getItemCount() - 1) {
                    mRequest.enqueue(mRequestCallback, lastPosition, mFragment.getPageItemCount());
                }
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
        if (mRequest != null && mAdapter.getItemCount() == mAdapter.getItemStablePosition() + 1) {
            mRequest.cancel();
            mRequest.enqueue(mRequestCallback);
        }
        if (mFragment.canLoadMore()) {
            mFragment.getRecyclerView().removeOnScrollListener(mOnScrollListener);
            mFragment.getRecyclerView().addOnScrollListener(mOnScrollListener);
        }
    }
}
