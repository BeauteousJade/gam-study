package com.example.pby.gam_study.network;

import com.example.pby.gam_study.page.login.bean.CodeBean;

import io.reactivex.Observable;


public interface Service {
    Observable<CodeBean> getCode();
}
