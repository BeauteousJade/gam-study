package com.example.pby.gam_study.fragment.tabhost;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pby.gam_study.fragment.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class PageFragment extends BaseFragment implements TabHost {

    public static final String KEY_LAYOUT_ID = "key_layout_id";

    private int mLayoutId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mLayoutId = bundle.getInt(KEY_LAYOUT_ID);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return mLayoutId;
    }
}
