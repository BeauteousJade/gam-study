package com.example.pby.gam_study.page.modifyUserName;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class ModifyUserNameActivity extends BaseActivity {

    public static final String USER_ID = "user_id";

    private String mUserId;

    public static void startActivityForResult(BaseActivity activity, String userId, int requestCode) {
        Intent intent = new Intent(activity, ModifyUserNameActivity.class);
        intent.putExtra(USER_ID, userId);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onPrepare() {
        super.onPrepare();
        mUserId = getIntent().getStringExtra(USER_ID);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return ModifyUserNameFragment.newInstance(mUserId);
    }
}
