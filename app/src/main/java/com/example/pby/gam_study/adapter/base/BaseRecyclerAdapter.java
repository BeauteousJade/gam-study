package com.example.pby.gam_study.adapter.base;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.mvp.Presence;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.util.ColorUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseRecyclerAdapter<U> extends RecyclerView.Adapter<BaseViewHolder> implements Presence {

    protected List<U> mDataList;
    private BaseFragment mFragment;
    private BaseActivity mActivity;


    public BaseRecyclerAdapter(List<U> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(getItemViewLayout(viewType), viewGroup, false);
        final Presenter presenter = onCreatePresenter();
        presenter.create(this);
        return new BaseViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        baseViewHolder.mPresenter.unBind();
        baseViewHolder.itemView.setBackgroundColor(Color.parseColor(ColorUtil.generateRandomColor()));
        baseViewHolder.mPresenter.bind(onCreateContext(baseViewHolder, position), baseViewHolder.itemView);
    }

    public Object onCreateContext(@NonNull BaseViewHolder baseViewHolder, int position) {
        Context context = new Context();
        context.mItem = mDataList.get(position);
        context.mPosition = position;
        context.mItemView = baseViewHolder.itemView;
        return context;
    }

    public void setCurrentFragment(BaseFragment fragment) {
        mFragment = fragment;
    }

    public void setCurrentActivity(BaseActivity activity) {
        mActivity = activity;
    }

    public static class Context {
        @Provides(AccessIds.ITEM_POSITION)
        public int mPosition;
        @Provides(AccessIds.ITEM_DATA)
        public Object mItem;
        @Provides(AccessIds.ITEM_VIEW)
        public View mItemView;
    }


    public abstract int getItemViewLayout(int viewType);


    protected abstract Presenter onCreatePresenter();


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseFragment> T getCurrentFragment() {
        return (T) mFragment;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseActivity> T getCurrentActivity() {
        return (T) mActivity;
    }

    @Override
    public View getRootView() {
        return mFragment.getView();
    }

    @Override
    public Resources getCurrentResources() {
        return mFragment.getResources();
    }

}
