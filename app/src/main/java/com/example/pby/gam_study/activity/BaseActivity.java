package com.example.pby.gam_study.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends AppCompatActivity {

    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        replaceFragment();
        if (canRegisterEvent()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (canRegisterEvent()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    protected final void replaceFragment() {
        replaceFragment(getContentFragmentId(), buildCurrentFragment());
    }

    protected final <T extends BaseFragment> void replaceFragment(T fragment) {
        replaceFragment(getContentFragmentId(), fragment);
    }

    protected final <T extends BaseFragment> void replaceFragment(@IdRes int containerId, T fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mCurrentFragment = fragment;
        transaction.replace(containerId, fragment);
        transaction.commitAllowingStateLoss();
    }

    protected boolean canRegisterEvent() {
        return false;
    }

    @SuppressWarnings("unchecked")
    public final <T extends BaseFragment> T getCurrentFragment() {
        return (T) mCurrentFragment;
    }

    public abstract BaseFragment buildCurrentFragment();

    @IdRes
    public int getContentFragmentId() {
        return R.id.fragment_content;
    }

    @LayoutRes
    public int getLayoutId() {
        return R.layout.activity_base;
    }

}
