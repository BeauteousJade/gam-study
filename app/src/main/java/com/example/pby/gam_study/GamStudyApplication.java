package com.example.pby.gam_study;

import android.app.Application;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;

public class GamStudyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NIMClient.init(this, null, SDKOptions.DEFAULT);
    }
}
