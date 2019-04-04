package com.example.pby.gam_study.page.userlist;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.page.userlist.fragment.FansListFragment;
import com.example.pby.gam_study.page.userlist.fragment.FollowUserListFragment;

import androidx.annotation.IntDef;

public class UserListActivity extends BaseActivity {

    private static final String TYPE = "type";
    public static final String USER_ID = "user_id";

    @ListType
    private int mType;

    public static void startActivity(BaseActivity activity, @ListType int type) {
        Intent intent = new Intent(activity, UserListActivity.class);
        intent.putExtra(TYPE, type);
        activity.startActivity(intent);
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mType = getIntent().getIntExtra(TYPE, 0);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        switch (mType) {
            case ListType.TYPE_FOLLOW:
                return FollowUserListFragment.newInstance(LoginManager.getCurrentUser().getId());
            case ListType.TYPE_FANS:
                return FansListFragment.newInstance(LoginManager.getCurrentUser().getId());
        }
        return null;
    }

    @IntDef({ListType.TYPE_FANS, ListType.TYPE_FOLLOW})
    public @interface ListType {
        int TYPE_FOLLOW = 1;
        int TYPE_FANS = 2;
    }
}
