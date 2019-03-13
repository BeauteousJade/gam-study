package com.example.pby.gam_study.page;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.page.home.HomeFragment;
import com.example.pby.gam_study.page.login.LoginEvent;
import com.example.pby.gam_study.page.login.LoginFragment;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.ToastUtil;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class HomeActivity extends BaseActivity {

    private final OnActivityResultListener mOnActivityResultListener = (requestCode, resultCode, data) -> {
        Tencent.onActivityResultData(requestCode, resultCode, data, null);
        return true;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        addOnActivityResultListener(mOnActivityResultListener);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != 100) {
            if (!ArrayUtil.isEmpty(permissions) && !ArrayUtil.isEmpty(grantResults)) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.error(this, "权限拒绝！");
                        finish();
                        return;
                    }
                }
            }
        }
    }

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
    protected boolean canRegisterEvent() {
        return true;
    }
}
