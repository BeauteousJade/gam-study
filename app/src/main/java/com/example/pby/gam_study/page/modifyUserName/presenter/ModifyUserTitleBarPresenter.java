package com.example.pby.gam_study.page.modifyUserName.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.DialogFactory;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.home.page.mine.presenter.adapter.UserNamePresenter;
import com.example.pby.gam_study.page.modifyUserName.ModifyUserNameRequest;
import com.example.pby.gam_study.util.StringUtil;
import com.example.pby.gam_study.util.ToastUtil;
import com.example.pby.gam_study.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyUserTitleBarPresenter extends Presenter {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.nick_name)
    EditText mEditText;

    @Inject(AccessIds.USER_ID)
    String mUserId;


    private ModifyUserNameRequest mModifyUserNameRequest;
    private GamDialogFragment mLoadDialogFragment;

    private final RequestCallback<Boolean> mRequestCallback = new RequestCallback<Boolean>() {
        @Override
        public void onResult(Response<Boolean> response) {
            mLoadDialogFragment.dismiss();
            if (response.getError() == null && response.getData()) {
                Intent intent = new Intent();
                intent.putExtra(UserNamePresenter.USER_NAME, mEditText.getText().toString());
                getCurrentActivity().setResult(Activity.RESULT_OK, intent);

                getCurrentActivity().finish();
                ToastUtil.info(getCurrentActivity(), getString(R.string.modify_success));
            } else {
                ToastUtil.error(getCurrentActivity(), getString(R.string.modify_failure));
            }
        }
    };

    @Override
    protected void onCreate() {
        mLoadDialogFragment = DialogFactory.createLoadDialog(getCurrentActivity());
    }

    @Override
    protected void onBind() {
        mTitleBar.setLeftIcon(R.drawable.bg_back);
        mTitleBar.setRightIcon(R.drawable.bg_ok);
        mTitleBar.setTitle(getString(R.string.title_modify_user_name));

    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }

    @OnClick(R.id.right_icon)
    public void onRightClick(View view) {
        if (StringUtil.isEmpty(mEditText.getText())) {
            ToastUtil.error(getCurrentActivity(), R.string.nick_name_empty);
            return;
        }
        mModifyUserNameRequest = new ModifyUserNameRequest(mUserId, mEditText.getText().toString());
        mModifyUserNameRequest.enqueue(mRequestCallback);
        mLoadDialogFragment.show(getCurrentFragment().getChildFragmentManager(), "");
    }
}
