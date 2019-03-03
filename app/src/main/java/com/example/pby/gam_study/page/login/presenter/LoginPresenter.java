package com.example.pby.gam_study.page.login.presenter;

import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.factory.LoadDialogFactory;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.page.login.LoginEvent;
import com.example.pby.gam_study.page.login.request.UserRequest;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.util.SharedPreferencesUtil;
import com.example.pby.gam_study.util.ToastUtil;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;

import static com.example.pby.gam_study.manager.LoginManager.USER_ID;

public class LoginPresenter extends Presenter {
    private Tencent mTencent;
    private String mId;
    private GamDialogFragment mLoadDialog;
    private final UserRequest mUserRequest = new UserRequest();

    private final RequestCallback<User> mRequestCallback = response -> {
        if (response.getError() != null) {
            ToastUtil.info(getCurrentActivity(), ResourcesUtil.getString(getCurrentActivity(), R.string.login_failure));
        } else {
            LoginManager.setCurrentUser(response.getData());
            EventBus.getDefault().post(new LoginEvent(true));
            ToastUtil.info(getCurrentActivity(), ResourcesUtil.getString(getCurrentActivity(), R.string.login_success));
        }
        mLoadDialog.dismiss();
    };


    @Override
    protected void onCreate() {
        mTencent = LoginManager.getTencent(getCurrentActivity().getApplicationContext());
        mId = SharedPreferencesUtil.getString(getCurrentActivity(), LoginManager.USER_ID, null);
        mLoadDialog = LoadDialogFactory.getLoadDialog(getCurrentActivity());
    }

    @OnClick(R.id.qq_login)
    public void onQQLoginClick(View view) {
        mTencent.login(getCurrentActivity(), "all", new IUiListener() {
            @Override
            public void onComplete(Object o) {
                try {
                    final String openId = ((JSONObject) o).getString("openid");
                    mId = openId;
                    mUserRequest.cancel();
                    mUserRequest.setId(openId);
                    mUserRequest.enqueue(mRequestCallback);
                    mLoadDialog.show(getCurrentFragment().getChildFragmentManager(), "");
                    SharedPreferencesUtil.putString(getCurrentActivity(), USER_ID, mId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    protected void onDestroy() {
        mUserRequest.cancel();
    }
}
