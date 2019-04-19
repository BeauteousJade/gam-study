package com.example.pby.gam_study.page.shareFans.presenter;

import android.view.View;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.DialogFactory;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.chat.request.SendMessageRequest;
import com.example.pby.gam_study.util.ToastUtil;

import butterknife.OnClick;

public class ShareUserItemClickPresenter extends Presenter implements View.OnClickListener {

    @Inject(AccessIds.ITEM_DATA)
    User mUser;

    private GamDialogFragment mLoadDialog;
    private GamDialogFragment mOptionDialog;

    private final RequestCallback<Boolean> mRequestCallback = new RequestCallback<Boolean>() {
        @Override
        public void onResult(Response<Boolean> response) {
            if (response.getError() == null && response.getData()) {
                ToastUtil.error(getCurrentActivity(), R.string.share_success);
            } else {
                ToastUtil.error(getCurrentActivity(), R.string.share_failure);
            }
            mLoadDialog.dismiss();
            getCurrentActivity().finish();
        }
    };

    @Override
    protected void onCreate() {
        mLoadDialog = DialogFactory.createLoadDialog(getCurrentActivity());
        mOptionDialog = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER, R.layout.dialog_share_fans)
                .setAnchorView(getCurrentActivity().findViewById(android.R.id.content))
                .setCanceledOnTouchOutside(false)
                .setOnViewClickListener(this, R.id.cancel, R.id.sure)
                .build();
    }


    @OnClick(R.id.item_container)
    public void onItemClick(View view) {
        mOptionDialog.show(getCurrentFragment().getChildFragmentManager(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:
                sendMessage();
                break;
        }
        mOptionDialog.dismiss();
    }

    private void sendMessage() {
        mLoadDialog.show(getCurrentFragment().getChildFragmentManager(), "");
        Message message = new Message();
        message.setToUser(mUser);
        message.setFromUser(LoginManager.getCurrentUser());
        message.setContent(getString(R.string.share_content));
        message.setSendUserId(LoginManager.getCurrentUser().getId());
        message.setTime(System.currentTimeMillis());
        SendMessageRequest request = new SendMessageRequest(message);
        request.enqueue(mRequestCallback);
    }
}
