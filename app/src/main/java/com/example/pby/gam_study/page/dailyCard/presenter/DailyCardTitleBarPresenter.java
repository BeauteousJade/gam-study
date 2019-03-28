package com.example.pby.gam_study.page.dailyCard.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

public class DailyCardTitleBarPresenter extends Presenter {

    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.left_icon)
    ImageView mLeftIcon;

    @Override
    protected void onBind() {
        mTitleView.setText(getString(R.string.daily_card));
        mLeftIcon.setImageDrawable(getDrawable(R.drawable.bg_back));
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }
}
