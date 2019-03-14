package com.example.pby.gam_study.page.sendPost;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class SendPostActivity extends BaseActivity {


    public static void startActivity(BaseActivity activity) {
        Intent intent = new Intent(activity, SendPostActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return SendPostFragment.newInstance();
    }
}
