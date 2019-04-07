package com.example.pby.gam_study.page.home.page.mine.presenter.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.RequestCode;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.modifyUserName.ModifyUserNameActivity;
import com.example.pby.gam_study.util.StringUtil;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class UserNamePresenter extends Presenter {

    @BindView(R.id.nick_name)
    TextView mNameView;

    public static final String USER_NAME = "user_name";


    private BaseActivity.OnActivityResultListener mOnActivityResultListener = new BaseActivity.OnActivityResultListener() {
        @Override
        public boolean onResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == RequestCode.REQUEST_MODIFY_NAME && resultCode == Activity.RESULT_OK && data != null) {
                String name = data.getStringExtra(USER_NAME);
                if (!StringUtil.isEmpty(name)) {
                    mNameView.setText(name);
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate() {
        getCurrentActivity().addOnActivityResultListener(mOnActivityResultListener);
    }

    @Override
    protected void onBind() {

    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);

    }

    @OnClick(R.id.nick_name)
    public void onNameClick(View view) {
        ModifyUserNameActivity.startActivityForResult(getCurrentActivity(), LoginManager.getCurrentUser().getId(), RequestCode.REQUEST_MODIFY_NAME);
    }
}
