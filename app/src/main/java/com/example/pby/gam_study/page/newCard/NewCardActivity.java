package com.example.pby.gam_study.page.newCard;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class NewCardActivity extends BaseActivity {


    public static void startActivity(BaseActivity activity) {
        Intent intent = new Intent(activity, NewCardActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return new NewCardFragment();
    }
}
