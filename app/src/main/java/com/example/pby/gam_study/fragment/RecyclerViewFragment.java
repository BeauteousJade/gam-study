package com.example.pby.gam_study.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.util.ArrayUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import butterknife.BindView;

public abstract class RecyclerViewFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mRecyclerAdapter;
    private ItemTouchHelper.Callback mCallback;
    private ItemTouchHelper mItemTouchHelper;
    private SnapHelper mSnapHelper;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<? extends RecyclerView.ItemDecoration> mItemDecorationList;
    private final Observable mObservable = new Observable();

    @Override
    public void onPrepareBaseContext() {
        mRecyclerAdapter = onCreateAdapter();
        mCallback = onCreateCallback();
        mSnapHelper = onCreateSnapHelper();
        mItemTouchHelper = onCreateItemTouchHelper();
        mLayoutManager = onCreateLayoutManager();
        mItemDecorationList = onCreateItemDecoration();

        mRecyclerAdapter.setCurrentActivity((BaseActivity) requireActivity());
        mRecyclerAdapter.setCurrentFragment(this);
        mRecyclerAdapter.setObservable(mObservable);
        mRecyclerAdapter.setRecyclerView(mRecyclerView);

        putExtra(AccessIds.RECYCLER_VIEW, mRecyclerView);
        putExtra(AccessIds.RECYCLER_ADAPTER, mRecyclerAdapter);
        putExtra(AccessIds.LAYOUT_MANAGER, mLayoutManager);
        putExtra(AccessIds.OBSERVABLE, mObservable);
        putExtra(AccessIds.CALLBACK, mCallback);
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
        if (mSnapHelper != null) {
            mSnapHelper.attachToRecyclerView(mRecyclerView);
        }
    }

    @Override
    public void onDestroyView() {
        mRecyclerAdapter.onDestroy(getRecyclerView());
        super.onDestroyView();
    }

    protected PagerSnapHelper onCreateSnapHelper() {
        return null;
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

    public final SnapHelper getSnapHelper() {
        return mSnapHelper;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

}
