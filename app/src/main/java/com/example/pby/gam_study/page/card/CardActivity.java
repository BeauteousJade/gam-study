package com.example.pby.gam_study.page.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.util.ResourcesUtil;

public class CardActivity extends BaseActivity {

    private static final String KIND_ID = "kind_id";
    private static final String KIND_NAME = "kind_name";

    public static void startActivity(BaseActivity activity, String kindId, String kindName) {
        Intent intent = new Intent(activity, CardActivity.class);
        intent.putExtra(KIND_ID, kindId);
        intent.putExtra(KIND_NAME, kindName);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        Intent intent = getIntent();
        return CardFragment.newInstance(intent.getStringExtra(KIND_ID), intent.getStringExtra(KIND_NAME));
    }
}
