package com.example.pby.gam_study.page.home.page.mine.presenter.mine;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.util.ResourcesUtil;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class MineRefreshPresenter extends Presenter {

    @Inject(AccessIds.REQUEST)
    Request mRequest;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;


    private final SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @SuppressWarnings("unchecked")
        @Override
        public void onRefresh() {
            mRequest.cancel();
            mRequest.enqueue();
        }
    };

    @Override
    protected void onBind() {
        mRefreshLayout.setColorSchemeColors(ResourcesUtil.getColor(getCurrentActivity(), R.color.color_main_blue));
        mRefreshLayout.setProgressBackgroundColorSchemeColor(ResourcesUtil.getColor(getCurrentActivity(), R.color.white));
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setRefreshing(true);

    }
}
