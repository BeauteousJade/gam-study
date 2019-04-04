package com.example.pby.gam_study.page.kind;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class KindActivity extends BaseActivity {

    private static final String USER_ID = "user_id";

    private String mUserId;

    public static void startActivity(BaseActivity activity, String userId) {
        Intent intent = new Intent(activity, KindActivity.class);
        intent.putExtra(USER_ID, userId);
        activity.startActivity(intent);
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mUserId = getIntent().getStringExtra(USER_ID);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return MineKindFragment.newInstance(mUserId);
    }
}
