package com.example.pby.gam_study.mvp;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.inject.Blade;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Presenter {
    private List<Presenter> mPresenterList;
    private Presence mPresence;
    private Unbinder mUnBinder;

    public final void create(Presence presence) {
        mPresence = presence;
        onCreate();
        if (!ArrayUtil.isEmpty(mPresenterList)) {
            for (Presenter presenter : mPresenterList) {
                presenter.create(presence);
            }
        }
    }

    protected void onCreate() {
    }

    public final void bind(Object source, View view) {
        if (mUnBinder == null) {
            mUnBinder = ButterKnife.bind(this, view);
        }
        Blade.inject(this, source);
        onBind();
        if (!ArrayUtil.isEmpty(mPresenterList)) {
            for (Presenter presenter : mPresenterList) {
                presenter.bind(source, view);
            }
        }
    }

    protected void onBind() {
    }

    public final void unBind() {
        if (mUnBinder != null) {
            onUnBind();
            if (!ArrayUtil.isEmpty(mPresenterList)) {
                for (Presenter presenter : mPresenterList) {
                    presenter.unBind();
                }
            }
        }

    }

    protected void onUnBind() {

    }

    public final void destroy() {
        onDestroy();
        mUnBinder.unbind();
        mUnBinder = null;
        mPresence = null;
        if (!ArrayUtil.isEmpty(mPresenterList)) {
            for (Presenter presenter : mPresenterList) {
                presenter.destroy();
            }
        }
    }

    protected void onDestroy() {
    }

    public final void add(@NonNull Presenter presenter) {
        if (mPresenterList == null) {
            mPresenterList = new ArrayList<>();
        }
        mPresenterList.add(presenter);
    }

    public final BaseFragment getCurrentFragment() {
        return mPresence.getCurrentFragment();
    }

    public final BaseActivity getCurrentActivity() {
        return mPresence.getCurrentActivity();
    }

    public final View getRootView() {
        return mPresence.getRootView();
    }

    public Resources getResources() {
        return mPresence.getCurrentResources();
    }

    public String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends Drawable> T getDrawable(@DrawableRes int id) {
        return (T) getResources().getDrawable(id);
    }

    public int getDimensionPixelSize(@DimenRes int id) {
        return getResources().getDimensionPixelSize(id);
    }

    public int getColor(@ColorRes int id) {
        return getResources().getColor(id);
    }
}
