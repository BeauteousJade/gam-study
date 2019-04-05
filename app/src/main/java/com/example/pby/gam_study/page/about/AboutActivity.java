package com.example.pby.gam_study.page.about;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class AboutActivity extends BaseActivity {

    public static void startActivity(BaseActivity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return AboutFragment.newInstance();
    }
}
