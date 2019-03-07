package com.example.pby.gam_study.page.newKind;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class NewKindActivity extends BaseActivity {

    public static void startAtivity(BaseActivity activity) {
        Intent intent = new Intent(activity, NewKindActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public BaseFragment buildCurrentFragment() {
        return new NewKindFragment();
    }
}
