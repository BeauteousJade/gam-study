package com.example.pby.gam_study.page.post.presenter;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;
import com.example.pby.gam_study.page.post.request.FindRequest;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class PostRefreshPresenter extends Presenter {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Inject(AccessIds.REQUEST)
    FindRequest mRequest;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    PostAdapter mAdapter;

//    private final SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
//        @SuppressWarnings("unchecked")
//        @Override
//        public void onRefresh() {
//            mRequest.cancel();
//            mRequest.enqueue(mRequestCallback);
//        }
//    };
//
//
//    private final RequestCallback<ResponseBody> mRequestCallback = new RequestCallback<ResponseBody>() {
//        @Override
//        public void onResult(Response<ResponseBody> response) {
//            if (response.getError() == null && response.getData() != null) {
//                try {
//                    String body = response.getData().string();
//                    mAdapter.setItemList(GsonUtil.fromJson(body, new ParameterizedTypeImpl(List.class, new Class[]{Post.class})));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            mRefreshLayout.setRefreshing(false);
//        }
//    };
//
//    @Override
//    protected void onBind() {
//        mRefreshLayout.setColorSchemeColors(ResourcesUtil.getColor(getCurrentActivity(), R.color.color_main_blue));
//        mRefreshLayout.setProgressBackgroundColorSchemeColor(ResourcesUtil.getColor(getCurrentActivity(), R.color.white));
//        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
//        mRefreshLayout.setRefreshing(true);
//        mRequest.enqueue(mRequestCallback);
//    }
}
