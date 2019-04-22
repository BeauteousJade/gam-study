package com.example.pby.gam_study.page.chat.presenter.chat;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.chat.ChatAdapter;
import com.example.pby.gam_study.page.chat.request.ChatRequest;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class ChatRefreshPresenter extends Presenter {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Inject(AccessIds.RECYCLER_ADAPTER)
    ChatAdapter mAdapter;
    @Inject(AccessIds.USER)
    User mUser;
    @Inject(AccessIds.RECYCLER_VIEW)
    RecyclerView mRecyclerView;

    private static final long AN_HOUR_MILLIS = 60 * 60 * 1000;

    private ChatRequest mChatRequest;
    private int mRequestCount = 1;

    private final RequestCallback<List<Message>> mCallback = new RequestCallback<List<Message>>() {
        @Override
        public void onResult(Response<List<Message>> response) {
            if (response.getError() == null && !ArrayUtil.isEmpty(response.getData())) {
                if (mAdapter.getDataList().isEmpty()) {
                    mAdapter.setItemList(response.getData());
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                } else {
                    mAdapter.addItemList(0, response.getData());
                }
                if (mRefreshLayout != null) {
                    mRefreshLayout.setRefreshing(false);
                }
                mRequestCount = 1;
            } else if (response.getError() == null && ArrayUtil.isEmpty(response.getData()) && mRequestCount < 10) {
                if (mChatRequest != null) {
                    mChatRequest = new ChatRequest(mUser.getId(), mChatRequest.getStartTime() - mRequestCount * AN_HOUR_MILLIS, mChatRequest.getStartTime());
                } else {
                    mChatRequest = new ChatRequest(mUser.getId(), System.currentTimeMillis() - mRequestCount * AN_HOUR_MILLIS, System.currentTimeMillis());
                }
                mChatRequest.enqueue(this);
                mRequestCount++;
            } else {
                mRequestCount = 1;
                if (mRefreshLayout != null) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        }
    };

    private final SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @SuppressWarnings("unchecked")
        @Override
        public void onRefresh() {
            if (mChatRequest != null) {
                mChatRequest = new ChatRequest(mUser.getId(), mChatRequest.getStartTime() - mRequestCount * AN_HOUR_MILLIS, mChatRequest.getStartTime());
            } else {
                mChatRequest = new ChatRequest(mUser.getId(), System.currentTimeMillis() - mRequestCount * AN_HOUR_MILLIS, System.currentTimeMillis());
            }
            mChatRequest.enqueue(mCallback);
        }
    };

    @Override
    protected void onBind() {
        mRefreshLayout.setColorSchemeColors(ResourcesUtil.getColor(getCurrentActivity(), R.color.color_main_blue));
        mRefreshLayout.setProgressBackgroundColorSchemeColor(ResourcesUtil.getColor(getCurrentActivity(), R.color.white));
        if (mChatRequest != null) {
            mChatRequest.cancel();
        }
        mChatRequest = new ChatRequest(mUser.getId(), System.currentTimeMillis() - AN_HOUR_MILLIS, System.currentTimeMillis());
        mChatRequest.enqueue(mCallback);
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }
}
