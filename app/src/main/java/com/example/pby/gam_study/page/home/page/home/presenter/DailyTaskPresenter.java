package com.example.pby.gam_study.page.home.page.home.presenter;

import android.widget.Button;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.DailyTask;
import com.example.pby.gam_study.widget.ProgressBar;

import butterknife.BindView;

public class DailyTaskPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    DailyTask mDailyTask;


    @BindView(R.id.item_text)
    TextView mTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.button)
    Button mButton;

    @Override
    protected void onBind() {
        // 签到
        if (mDailyTask.getDailyCard() == null) {
            mButton.setSelected(mDailyTask.isSign());
            mButton.setEnabled(!mDailyTask.isSign());
            mButton.setText(mDailyTask.isSign() ? getString(R.string.signed) : getString(R.string.sign));
            mProgressBar.setMaxProgress(1);
            mProgressBar.setCurrentProgress(mDailyTask.isSign() ? 1 : 0);
            mTextView.setText(getString(R.string.daily_sign));
        } else {
            mProgressBar.setMaxProgress(mDailyTask.getDailyTaskCount());
            mProgressBar.setCurrentProgress(mDailyTask.getDailyTaskCount() - mDailyTask.getDailyCard().size());
            boolean isComplete = mProgressBar.getCurrentProgress() == mProgressBar.getMaxProgress();
            mButton.setSelected(isComplete && mDailyTask.isSign());
            mButton.setEnabled(!mDailyTask.isSign() || mProgressBar.getMaxProgress() != 0);
            mButton.setText(isComplete ? getString(R.string.completed) : getString(R.string.complete));
            mTextView.setText(getString(R.string.daily_card));
        }
    }
}