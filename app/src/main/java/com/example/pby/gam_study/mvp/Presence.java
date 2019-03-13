package com.example.pby.gam_study.mvp;

import android.content.res.Resources;
import android.view.View;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public interface Presence {

    <T extends BaseFragment> T getCurrentFragment();

    <T extends BaseActivity> T getCurrentActivity();

    View getRootView();

    Resources getCurrentResources();
}