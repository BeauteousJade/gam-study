package com.example.pby.gam_study.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class NetWorkManager {
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl("http://192.168.1.129:8080")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(LenientGsonConverterFactory.create())
            .build();

    public static <T> T getService(Class<? extends T> clazz) {
        return RETROFIT.create(clazz);
    }
}
