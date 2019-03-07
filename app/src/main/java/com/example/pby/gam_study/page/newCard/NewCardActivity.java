package com.example.pby.gam_study.page.newCard;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class NewCardActivity extends BaseActivity {

    public static final String KIND_ID = "kind_id";

    private String mKindId;

    public static void startActivity(BaseActivity activity, String kindId) {
        Intent intent = new Intent(activity, NewCardActivity.class);
        intent.putExtra(KIND_ID, kindId);
        activity.startActivity(intent);
    }


    @Override
    protected void onPrepare() {
        mKindId = getIntent().getStringExtra(KIND_ID);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return NewCardFragment.newInstance(mKindId);
    }
}
