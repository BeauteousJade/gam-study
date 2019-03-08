package com.example.pby.gam_study.fragment;

import com.example.annation.Provides;
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mContext = super.onCreateBaseContext();
        context.mRequest = mRequest;
        return (T) context;
    }

    public static class Context {
        @Provides(deepProvides = true)
        public RecyclerViewFragment.Context mContext;
        @Provides(value = AccessIds.REQUEST)
        public Request mRequest;
    }

    public abstract Request onCreateRequest();

    protected boolean canLoadMore() {
        return false;
    }

    protected int getPageItemCount() {
        return 0;
    }
}
