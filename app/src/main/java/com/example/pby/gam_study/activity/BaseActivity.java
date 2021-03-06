package com.example.pby.gam_study.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.key.KeyboardHeightObserver;
import com.example.pby.gam_study.util.key.KeyboardHeightProvider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseActivity extends AppCompatActivity {

    private BaseFragment mCurrentFragment;
    private List<OnActivityResultListener> mListenerList;
    private KeyboardHeightProvider mKeyboardHeightProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onPrepare();
        replaceFragment();
        if (canRegisterEvent()) {
            EventBus.getDefault().register(this);
        }
        View fragmentContent = findViewById(R.id.fragment_content);
        int navigationBarHeight = DisplayUtil.getNavigationBarHeight(this);
        if (DisplayUtil.getNavigationBarHeight(this) != 0 && fragmentContent != null) {
            fragmentContent.setPadding(fragmentContent.getPaddingLeft(),
                    fragmentContent.getPaddingTop(),
                    fragmentContent.getPaddingRight(),
                    fragmentContent.getPaddingBottom() + navigationBarHeight);
        }
    }

    @Override
    protected void onDestroy() {
        if (canRegisterEvent()) {
            EventBus.getDefault().unregister(this);
        }
        if (mKeyboardHeightProvider != null) {
            mKeyboardHeightProvider.close();
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

    @CallSuper
    protected void onPrepare() {
        if (supportKeyboardHeightProvider()) {
            mKeyboardHeightProvider = new KeyboardHeightProvider(this);
            findViewById(android.R.id.content).post(() -> mKeyboardHeightProvider.start());
        }
    }

    protected boolean supportKeyboardHeightProvider() {
        return false;
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

    public void addKeyboardHeightObserver(KeyboardHeightObserver observer) {
        if (mKeyboardHeightProvider != null) {
            mKeyboardHeightProvider.addKeyboardHeightObserver(observer);
        }
    }

    public void removeKeyboardHeightObserver(KeyboardHeightObserver observer) {
        if (mKeyboardHeightProvider != null) {
            mKeyboardHeightProvider.removeKeyboardHeightObserver(observer);
        }
    }

    public boolean isKeyboardShow() {
        if (mKeyboardHeightProvider != null) {
            return mKeyboardHeightProvider.isShow();
        }
        return false;
    }

    @FunctionalInterface
    public interface OnActivityResultListener {
        boolean onResult(int requestCode, int resultCode, @Nullable Intent data);
    }

}
