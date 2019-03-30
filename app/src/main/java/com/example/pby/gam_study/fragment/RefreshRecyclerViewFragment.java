package com.example.pby.gam_study.fragment;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;

public abstract class RefreshRecyclerViewFragment extends RecyclerViewFragment {

    private Request mRequest;

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        mRequest = onCreateRequest();
        putExtra(AccessIds.REQUEST, mRequest);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview_refresh;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new RefreshPresenter());
        return presenter;
    }

    public abstract Request onCreateRequest();

    protected boolean canLoadMore() {
        return false;
    }

    protected int getPageItemCount() {
        return 0;
    }
}
