package com.example.pby.gam_study.page.sendPost;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class SendPostActivity extends BaseActivity {

    public static String POST_CONTENT = "post_content";
    private String mPostContent;


    public static void startActivity(BaseActivity activity, String postContent) {
        Intent intent = new Intent(activity, SendPostActivity.class);
        intent.putExtra(POST_CONTENT, postContent);
        activity.startActivity(intent);
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mPostContent = getIntent().getStringExtra(POST_CONTENT);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return SendPostFragment.newInstance(mPostContent);
    }
}
