package com.example.pby.gam_study.page.newKind;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class NewKindActivity extends BaseActivity {
    @Override
    public BaseFragment buildCurrentFragment() {
        return new NewKindFragment();
    }
}
