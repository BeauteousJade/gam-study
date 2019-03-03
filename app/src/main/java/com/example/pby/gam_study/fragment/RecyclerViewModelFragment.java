package com.example.pby.gam_study.fragment;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pby.gam_study.inter.ViewModelLifecycle;

public abstract class RecyclerViewModelFragment<T extends ViewModel> extends RecyclerViewFragment implements ViewModelLifecycle<T> {

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
}
