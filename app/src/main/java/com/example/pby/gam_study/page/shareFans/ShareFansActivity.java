package com.example.pby.gam_study.page.shareFans;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class ShareFansActivity extends BaseActivity {

    public static void startActivity(BaseActivity activity){
        Intent intent = new Intent(activity, ShareFansActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return ShareFansFragment.newInstance();
    }
}
