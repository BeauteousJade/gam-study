package com.example.pby.gam_study.page.post;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class PostActivity extends BaseActivity {

    public static void startActivity(BaseActivity activity) {
        Intent intent = new Intent(activity, PostActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return PostFragment.newInstance();
    }

    @Override
    protected boolean supportKeyboardHeightProvider() {
        return true;
    }
}
