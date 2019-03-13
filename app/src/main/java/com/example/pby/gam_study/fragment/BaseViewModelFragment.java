package com.example.pby.gam_study.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.inter.ViewModelLifecycle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModelFragment<T extends ViewModel> extends BaseFragment implements ViewModelLifecycle<T> {

    private T mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = onCreateViewModel();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onViewModelCreated(mViewModel);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public abstract T onCreateViewModel();

    @Override
    public abstract void onViewModelCreated(T t);


    public T getViewMdel() {
        return mViewModel;
    }

    public static class Context<T extends ViewModel> {
        @Provides(AccessIds.VIEW_MODEL)
        public T mViewModel;
    }
}
