package com.example.pby.gam_study.page.chat;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.network.bean.User;

public class ChatActivity extends BaseActivity {

    public static final String TO_USER = "to_user";
    private User mToUser;

    public static void startActivity(BaseActivity activity, User toUser) {
        activity.startActivity(getStartActivityIntent(activity, toUser));
    }

    public static Intent getStartActivityIntent(BaseActivity activity, User toUser) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(TO_USER, toUser);
        return intent;
    }


    @Override
    protected void onPrepare() {
        super.onPrepare();
        mToUser = getIntent().getParcelableExtra(TO_USER);
    }

    @Override
    protected boolean supportKeyboardHeightProvider() {
        return true;
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return ChatFragment.newInstance(mToUser);
    }
}
