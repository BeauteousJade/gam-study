package com.example.pby.gam_study.page.kind;

import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MineKindTitleBarPresenter extends Presenter {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    @Override
    protected void onBind() {
        mTitleBar.setTitle(getString(R.string.title_mine_kind));
        mTitleBar.setLeftIcon(R.drawable.bg_back);
        mTitleBar.setRightIcon(R.drawable.bg_add);
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }
}
