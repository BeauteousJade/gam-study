package com.example.pby.gam_study.page;

import android.content.Intent;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.page.home.HomeFragment;
import com.example.pby.gam_study.page.login.LoginEvent;
import com.example.pby.gam_study.page.login.LoginFragment;
import com.example.pby.gam_study.util.ToastUtil;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, null);
    }

    @Override
    protected boolean canRegisterEvent() {
        return true;
    }
}
