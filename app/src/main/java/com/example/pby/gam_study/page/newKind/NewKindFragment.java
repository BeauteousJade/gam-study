package com.example.pby.gam_study.page.newKind;

import android.support.v7.widget.RecyclerView;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.newKind.presenter.NewKindTitleBarPresenter;
import com.example.pby.gam_study.page.newKind.request.KindCoverRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewKindFragment extends RefreshRecyclerViewFragment {

    private final NewKindObservable mNewKindObservable = new NewKindObservable();

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        final List<NewKindItem> dataList = new ArrayList<>();
        dataList.add(new NewKindItem(false, ""));
        dataList.add(new NewKindItem(false, ""));
        return new NewKindAdapter(dataList, mNewKindObservable);
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createGridLayoutManagerIfEmpty(requireContext(), getRecyclerAdapter(), 3, (adapter, position) -> {
            if (getRecyclerAdapter().isShowEmpty() || position == 0 || position == 1) {
                return true;
            }
            return false;
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_title;
    }

    @Override
    public Object onCreateBaseContext() {
        Context context = new Context();
        context.mContext = (RefreshRecyclerViewFragment.Context) super.onCreateBaseContext();
        context.mNewKindObservable = mNewKindObservable;
        return context;
    }

    @Override
    public Request onCreateRequest() {
        return new KindCoverRequest();
    }


    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new NewKindTitleBarPresenter());
        return presenter;
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new GridItemDecoration());
    }

    public static class Context {
        @Provides(AccessIds.OBSERVABLE)
        public NewKindObservable mNewKindObservable;
        @Provides(deepProvides = true)
        public RefreshRecyclerViewFragment.Context mContext;
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }
}
