package com.example.pby.gam_study.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.widget.layoutManager.ItemTouchStatus;

import java.util.List;

import butterknife.BindView;

public abstract class RecyclerViewFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mRecyclerAdapter;
    private ItemTouchHelper.Callback mCallback;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<? extends RecyclerView.ItemDecoration> mItemDecorationList;
    private final Observable mObservable = new Observable();

    @Override
    public void onPrepareBaseContext() {
        mRecyclerAdapter = onCreateAdapter();
        mCallback = onCreateCallback();
        mItemTouchHelper = onCreateItemTouchHelper();
        mLayoutManager = onCreateLayoutManager();
        mItemDecorationList = onCreateItemDecoration();

        mRecyclerAdapter.setCurrentActivity((BaseActivity) requireActivity());
        mRecyclerAdapter.setCurrentFragment(this);
        mRecyclerAdapter.setObservable(mObservable);
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
        if (mItemTouchHelper != null) {
            mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        }
    }

    public ItemTouchHelper.Callback onCreateCallback() {
        return null;
    }

    protected ItemTouchHelper onCreateItemTouchHelper() {
        return null;
    }

    protected abstract BaseRecyclerAdapter onCreateAdapter();

    protected abstract RecyclerView.LayoutManager onCreateLayoutManager();

    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return null;
    }

    public final RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public final BaseRecyclerAdapter getRecyclerAdapter() {
        return mRecyclerAdapter;
    }

    public final RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public final ItemTouchHelper getItemTouchHelper() {
        return mItemTouchHelper;
    }

    public final ItemTouchHelper.Callback getCallback() {
        return mCallback;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mContext = super.onCreateBaseContext();
        context.mRecyclerView = mRecyclerView;
        context.mRecyclerAdapter = mRecyclerAdapter;
        context.mLayoutManager = mLayoutManager;
        context.mObservable = mObservable;
        context.mCallback = mCallback;
        return (T) context;
    }

    public static class Context {
        @Provides(deepProvides = true)
        public BaseFragment.Context mContext;
        @Provides(value = AccessIds.RECYCLER_VIEW)
        public RecyclerView mRecyclerView;
        @Provides(value = AccessIds.RECYCLER_ADAPTER)
        public RecyclerView.Adapter mRecyclerAdapter;
        @Provides(value = AccessIds.LAYOUT_MANAGER)
        public RecyclerView.LayoutManager mLayoutManager;
        @Provides(value = AccessIds.OBSERVABLE)
        public Observable mObservable;
        @Provides(value = AccessIds.CALLBACK)
        public ItemTouchHelper.Callback mCallback;
    }

}
