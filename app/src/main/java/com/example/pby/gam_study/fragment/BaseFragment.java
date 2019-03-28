package com.example.pby.gam_study.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.inter.BaseContextLifecycle;
import com.example.pby.gam_study.mvp.Presence;
import com.example.pby.gam_study.mvp.Presenter;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements Presence, BaseContextLifecycle {

    protected Presenter mPresenter;
    protected Object mContext;
    private Map<String, Object> mExtraMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onPrepare();
        mPresenter = onCreatePresenter();
        mPresenter.create(this);
        return inflater.inflate(getLayoutId(), container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        onPrepareBaseContext();
        mContext = onCreateBaseContext();
        mPresenter.bind(mContext, mExtraMap, view);
    }

    @Override
    public void onDestroyView() {
        mPresenter.unBind();
        mPresenter.destroy();
        super.onDestroyView();
    }


    public final void refresh() {
        mPresenter.unBind();
        mPresenter.bind(mContext, mExtraMap, getRootView());
    }


    public Presenter onCreatePresenter() {
        return new Presenter();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected void onPrepare() {

    }

    @Override
    public void onPrepareBaseContext() {
    }

    @SuppressWarnings("unchecked")
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mFragment = this;
        return (T) context;
    }

    public void putExtra(String id, Object object) {
        mExtraMap.put(id, object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseFragment> T getCurrentFragment() {
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseActivity> T getCurrentActivity() {
        return (T) getActivity();
    }

    @Override
    public View getRootView() {
        return getView();
    }

    @Override
    public Resources getCurrentResources() {
        return getResources();
    }

    public static class Context {
        @Provides(value = AccessIds.FRAGMENT)
        public Fragment mFragment;
    }
}
