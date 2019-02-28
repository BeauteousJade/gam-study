package com.example.pby.gam_study.inter;

import android.arch.lifecycle.ViewModel;

public interface ViewModelLifecycle<T extends ViewModel> {
    T onCreateViewModel();

    void onViewModelCreated(T t);
}
