package com.example.pby.gam_study.page.profile;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.network.bean.User;

public class UserProfileActivity extends BaseActivity {

    public static final String USER = "user";

    private User mUser;

    public static void startActivity(BaseActivity activity, User user) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        intent.putExtra(USER, user);
        activity.startActivity(intent);
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mUser = getIntent().getParcelableExtra(USER);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return UserProfileFragment.newInstance(mUser);
    }
}
