package com.example.pby.gam_study.inter;

public interface BaseContextLifecycle {

    /**
     * 先于{@link #onCreateBaseContext()}方法执行，在此方法主可以初始化一些BaseContext需要的变量，
     * 此方法初始化的变量之间不能有初始化顺序
     */
    void onPrepareBaseContext();

    Object onCreateBaseContext();
}
