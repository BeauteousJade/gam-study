package com.example.pby.gam_study.page;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.page.home.HomeFragment;
import com.example.pby.gam_study.page.login.LoginEvent;
import com.example.pby.gam_study.page.login.LoginFragment;
import com.example.pby.gam_study.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomeActivity extends BaseActivity {


    @Override
    public BaseFragment buildCurrentFragment() {
        return new LoginFragment();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginFinish(LoginEvent loginEvent) {
        if (loginEvent.loginResult) {
            replaceFragment(HomeFragment.newInstance());
        } else {
            ToastUtil.error(this, "登录失败！");
        }
    }

    @Override
    public int getContentFragmentId() {
        return R.id.fragment_content;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }
}
