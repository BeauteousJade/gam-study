package com.example.pby.gam_study.page.home.page.mine.presenter.adapter;

import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.util.DisplayUtil;

import butterknife.OnClick;

public class UserHeadClickPresenter extends Presenter implements View.OnClickListener {

    private GamDialogFragment mDialogFragment;

    @Override
    protected void onCreate() {
        mDialogFragment = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER_BOTTOM, R.layout.menu_avatar)
                .setAnchorView(getCurrentActivity().findViewById(android.R.id.content))
                .setWindowWidth(DisplayUtil.getScreenWidth(getCurrentActivity()))
                .setAnimationStyle(R.style.menu_show_animation)
                .setOnViewClickListener(this, R.id.update_avatar, R.id.cancel)
                .build();
    }

    @OnClick(R.id.avatar)
    public void onAvatarClick(View view) {
        mDialogFragment.show(getCurrentFragment().getChildFragmentManager(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_avatar:
                break;
            case R.id.cancel:
                mDialogFragment.dismiss();
                break;
        }
    }
}
