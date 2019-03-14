package com.example.pby.gam_study.activity;

import android.content.Intent;
import android.os.Bundle;


import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseActivity extends AppCompatActivity {

    private BaseFragment mCurrentFragment;
    private List<OnActivityResultListener> mListenerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onPrepare();
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

    protected void onPrepare() {

    }

    @IdRes
    public int getContentFragmentId() {
        return R.id.fragment_content;
    }

    @LayoutRes
    public int getLayoutId() {
        return R.layout.activity_base;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mListenerList != null) {
            for (OnActivityResultListener listener : mListenerList) {
                if (listener.onResult(requestCode, resultCode, data)) {
                    return;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addOnActivityResultListener(OnActivityResultListener listener) {
        if (mListenerList == null) {
            mListenerList = new ArrayList<>();
        }
        mListenerList.add(listener);
    }

    public void removeOnActivityResultListener(OnActivityResultListener listener) {
        if (mListenerList != null) {
            mListenerList.remove(listener);
        }
    }

    @FunctionalInterface
    public interface OnActivityResultListener {
        boolean onResult(int requestCode, int resultCode, @Nullable Intent data);
    }

}
