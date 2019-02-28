package com.example.pby.gam_study.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.util.ArrayUtil;

import java.util.List;

import butterknife.BindView;

public abstract class RecyclerViewFragment<U extends RecyclerView.LayoutManager> extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mRecyclerAdapter;
    private U mLayoutManager;
    private List<? extends RecyclerView.ItemDecoration> mItemDecorationList;


    @Override
    public void onPrepareBaseContext() {
        Log.i("pby123", "name = " + this.getClass().getName() + " onPrepareBaseContext");
        mLayoutManager = onCreateLayoutManager();
        mRecyclerAdapter = onCreateAdapter();
        mItemDecorationList = onCreateItemDecoration();

        mRecyclerAdapter.setCurrentActivity((BaseActivity) requireActivity());
        mRecyclerAdapter.setCurrentFragment(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        if (!ArrayUtil.isEmpty(mItemDecorationList)) {
            for (RecyclerView.ItemDecoration itemDecoration : mItemDecorationList) {
                mRecyclerView.addItemDecoration(itemDecoration);
            }
        }
    }


    protected abstract BaseRecyclerAdapter onCreateAdapter();

    protected abstract U onCreateLayoutManager();

    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return null;
    }

    public final RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public final BaseRecyclerAdapter getRecyclerAdapter() {
        return mRecyclerAdapter;
    }

    public final U getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @CallSuper
    @Override
    public Object onCreateBaseContext() {
        Context context = new Context();
        context.mRecyclerView = mRecyclerView;
        context.mRecyclerAdapter = mRecyclerAdapter;
        context.mLayoutManager = mLayoutManager;
        return context;
    }

    public static class Context {
        @Provides(value = AccessIds.RECYCLER_VIEW)
        public RecyclerView mRecyclerView;
        @Provides(value = AccessIds.RECYCLER_ADAPTER)
        public RecyclerView.Adapter mRecyclerAdapter;
        @Provides(value = AccessIds.LAYOUT_MANAGER)
        public RecyclerView.LayoutManager mLayoutManager;
    }
}
