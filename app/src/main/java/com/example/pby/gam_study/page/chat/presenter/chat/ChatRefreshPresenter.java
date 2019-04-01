package com.example.pby.gam_study.page.chat.presenter.chat;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.page.chat.ChatAdapter;
import com.example.pby.gam_study.page.chat.request.ChatRequest;
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


    private Request<List<Message>> mChatRequest;

    private final RequestCallback<List<Message>> mCallback = response -> {
        if (response.getError() == null && response.getData() != null) {
            mAdapter.setItemList(response.getData());
            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    protected void onBind() {
        mRefreshLayout.setColorSchemeColors(ResourcesUtil.getColor(getCurrentActivity(), R.color.color_main_blue));
        mRefreshLayout.setProgressBackgroundColorSchemeColor(ResourcesUtil.getColor(getCurrentActivity(), R.color.white));
        if (mChatRequest != null) {
            mChatRequest.cancel();
        }
        mChatRequest = new ChatRequest(mUser.getId());
        mChatRequest.enqueue(mCallback);
        mRefreshLayout.setRefreshing(true);
    }
}
