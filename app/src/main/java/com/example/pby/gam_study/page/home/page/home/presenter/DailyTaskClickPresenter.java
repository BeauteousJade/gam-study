package com.example.pby.gam_study.page.home.page.home.presenter;

import android.view.View;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.network.bean.DailyTask;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.dailyCard.DailyCardActivity;
import com.example.pby.gam_study.page.home.page.home.request.CommitDailyTaskRequest;
import com.example.pby.gam_study.page.home.page.home.request.SignRequest;
import com.example.pby.gam_study.page.sendPost.SendPostActivity;
import com.example.pby.gam_study.page.shareFans.ShareFansActivity;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ToastUtil;

import java.util.ArrayList;

import butterknife.OnClick;

public class DailyTaskClickPresenter extends Presenter implements View.OnClickListener {

    @Inject(AccessIds.ITEM_POSITION)
    int mPosition;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter mAdapter;
    @Inject(AccessIds.ITEM_DATA)
    DailyTask mDailyTask;

    private final RequestCallback<Boolean> mRequestCallback = new RequestCallback<Boolean>() {

        @Override
        public void onResult(Response<Boolean> response) {
            if (response.getError() == null && response.getData()) {
                final DailyTask dailyTask = (DailyTask) mAdapter.getDataList().get(mPosition);
                dailyTask.setIsSign(true);
                mAdapter.notifyItemChanged(mPosition, "");
                mSignSuccessDialog.show(getCurrentFragment().getChildFragmentManager(), "");
            } else {
                ToastUtil.error(getCurrentActivity(), getString(R.string.sign_failure));
            }
        }
    };

    private final RequestCallback<Boolean> mCommitDailyTaskRequestCallback = new RequestCallback<Boolean>() {
        @Override
        public void onResult(Response<Boolean> response) {
            if (response.getError() == null && response.getData()) {
                final DailyTask dailyTask = (DailyTask) mAdapter.getDataList().get(mPosition);
                dailyTask.setIsSign(true);
                mAdapter.notifyItemChanged(mPosition, "");
                mCommitSuccessDialog.show(getCurrentFragment().getChildFragmentManager(), "");
            }
        }
    };
    private final Request<Boolean> mSignRequest = new SignRequest();

    private GamDialogFragment mSignSuccessDialog;
    private GamDialogFragment mCommitSuccessDialog;
    private GamDialogFragment mShareOptionDialog;
    private Request<Boolean> mCommitDailyTaskRequest;

    @Override
    protected void onCreate() {
        mSignSuccessDialog = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER, R.layout.dialog_sign)
                .setAnchorView(getRootView())
                .setOnViewClickListener(this, R.id.sure)
                .addTextEntry(R.id.blessing_text, getString(R.string.sign_success))
                .build();

        mCommitSuccessDialog = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER, R.layout.dialog_commit)
                .setAnchorView(getRootView())
                .setOnViewClickListener(this, R.id.share)
                .addTextEntry(R.id.blessing_text, getString(R.string.commit_success))
                .build();
        mShareOptionDialog = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER_BOTTOM, R.layout.dialog_share_options)
                .setAnchorView(getCurrentActivity().findViewById(android.R.id.content))
                .setOnViewClickListener(this, R.id.post_container, R.id.fans_container)
                .setAnimationStyle(R.style.menu_show_animation)
                .setWindowWidth(DisplayUtil.getScreenWidth(getCurrentActivity()))
                .build();
    }

    @Override
    protected void onBind() {

    }

    @Override
    protected void onUnBind() {
        mSignRequest.cancel();
        if (mCommitDailyTaskRequest != null) {
            mCommitDailyTaskRequest.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        mSignRequest.cancel();
        if (mCommitDailyTaskRequest != null) {
            mCommitDailyTaskRequest.cancel();
        }
        dismissDialog();
    }

    @OnClick(R.id.button)
    public void onButtonClick(View view) {
        if (mDailyTask.getDailyCard() == null) {
            mSignRequest.enqueue(mRequestCallback);
        } else {
            if (!ArrayUtil.isEmpty(mDailyTask.getDailyCard())) {
                DailyCardActivity.startActivity(getCurrentActivity(), (ArrayList<Card>) mDailyTask.getDailyCard());
            } else {
                mCommitDailyTaskRequest = new CommitDailyTaskRequest(LoginManager.getCurrentUser().getId());
                mCommitDailyTaskRequest.enqueue(mCommitDailyTaskRequestCallback);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:
                dismissDialog();
                break;
            case R.id.share:
                mShareOptionDialog.show(getCurrentFragment().getChildFragmentManager(), "");
                break;
            case R.id.post_container:
                SendPostActivity.startActivity(getCurrentActivity(), getString(R.string.share_content));
                dismissDialog();
                break;
            case R.id.fans_container:
                ShareFansActivity.startActivity(getCurrentActivity());
                dismissDialog();
                break;
        }
    }

    private void dismissDialog() {
        if (mSignSuccessDialog.isVisible()) {
            mSignSuccessDialog.dismiss();
        }
        if (mCommitSuccessDialog.isVisible()) {
            mCommitSuccessDialog.dismiss();
        }
        if (mShareOptionDialog.isVisible()) {
            mShareOptionDialog.dismiss();
        }
    }
}
