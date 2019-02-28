package com.example.pby.gam_study.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.inter.BaseContextLifecycle;
import com.example.pby.gam_study.mvp.Presence;
import com.example.pby.gam_study.mvp.Presenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements Presence, BaseContextLifecycle {

    private Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = onCreatePresenter();
        mPresenter.create(this);
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        onPrepareBaseContext();
        Log.i("pby123", "name = " + this.getClass().getName() + " onViewCreated");
        mPresenter.bind(onCreateBaseContext(), view);
    }


    @Override
    public void onDestroyView() {
        mPresenter.destroy();
        super.onDestroyView();
    }

    public Presenter onCreatePresenter() {
        return new Presenter();
    }

    @LayoutRes
    protected abstract int getLayoutId();


    @Override
    public void onPrepareBaseContext() {
    }

    public Object onCreateBaseContext() {
        return null;
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
}
