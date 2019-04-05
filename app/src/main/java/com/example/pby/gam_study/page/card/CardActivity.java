package com.example.pby.gam_study.page.card;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

import androidx.annotation.Nullable;

public class CardActivity extends BaseActivity {

    private static final String KIND_ID = "kind_id";
    private static final String TITLE = "kind_name";

    public static void startActivity(BaseActivity activity, @Nullable String kindId, String title) {
        Intent intent = new Intent(activity, CardActivity.class);
        intent.putExtra(KIND_ID, kindId);
        intent.putExtra(TITLE, title);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        Intent intent = getIntent();
        return CardFragment.newInstance(intent.getStringExtra(KIND_ID), intent.getStringExtra(TITLE));
    }
}
