package com.example.pby.gam_study.page.home.page.home.presenter;

import android.view.View;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.network.bean.DailyTask;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.dailyCard.DailyCardActivity;
import com.example.pby.gam_study.page.home.page.home.request.SignRequest;
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

        @SuppressWarnings("unchecked")
        @Override
        public void onResult(Response<Boolean> response) {
            if (response.getError() == null && response.getData()) {
                final DailyTask dailyTask = (DailyTask) mAdapter.getDataList().get(mPosition);
                dailyTask.setIsSign(true);
                mAdapter.setData(mPosition, dailyTask, false);
                mDialog.show(getCurrentFragment().getChildFragmentManager(), "");
            } else {
                ToastUtil.error(getCurrentActivity(), getString(R.string.sign_failure));
            }
        }
    };

    private GamDialogFragment mDialog;
    private final Request<Boolean> mSignRequest = new SignRequest();

    @Override
    protected void onCreate() {
        mDialog = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER, R.layout.dialog_sign_success)
                .setAnchorView(getRootView())
                .setOnViewClickListener(this, R.id.sure)
                .build();
    }

    @Override
    protected void onBind() {

    }

    @Override
    protected void onUnBind() {
        mSignRequest.cancel();
    }

    @Override
    protected void onDestroy() {
        mSignRequest.cancel();
    }

    @OnClick(R.id.button)
    public void onButtonClick(View view) {
        if (mDailyTask.getDailyCard() == null) {
            mSignRequest.enqueue(mRequestCallback);
        } else {
            DailyCardActivity.startActivity(getCurrentActivity(), (ArrayList<Card>) mDailyTask.getDailyCard());
        }
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
    }
}
