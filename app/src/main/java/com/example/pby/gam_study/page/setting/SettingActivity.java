package com.example.pby.gam_study.page.setting;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class SettingActivity extends BaseActivity {

    public static void startActivity(BaseActivity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public BaseFragment buildCurrentFragment() {
        return SettingFragment.newInstance();
    }
}
