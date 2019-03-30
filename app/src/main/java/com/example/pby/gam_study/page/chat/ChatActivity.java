package com.example.pby.gam_study.page.chat;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class ChatActivity extends BaseActivity {

    public static final String TO_USER_ID = "to_user_id";
    private String mToUserId;

    public static void startActivity(BaseActivity activity, String toUserId) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(TO_USER_ID, toUserId);
        activity.startActivity(intent);
    }


    @Override
    protected void onPrepare() {
        super.onPrepare();
        mToUserId = getIntent().getStringExtra(TO_USER_ID);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return ChatFragment.newInstance(mToUserId);
    }
}
