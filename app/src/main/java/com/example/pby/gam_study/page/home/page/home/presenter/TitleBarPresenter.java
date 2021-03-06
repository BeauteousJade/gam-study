package com.example.pby.gam_study.page.home.page.home.presenter;

import android.view.View;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.newKind.NewKindActivity;

import butterknife.OnClick;

public class TitleBarPresenter extends Presenter implements View.OnClickListener {

    @Inject
    String mEmpty = null;

    private GamDialogFragment mGamDialogFragment;

    @Override
    protected void onBind() {

    }

    @OnClick(R.id.right_icon)
    void onRightIconClick(View view) {
        if (mGamDialogFragment == null) {
            mGamDialogFragment = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_RIGHT_BOTTOM, R.layout.menu_home)
                    .setAnchorView(view)
                    .setOnViewClickListener(this, R.id.menu1)
                    .build();
        }
        mGamDialogFragment.show(getCurrentActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu1:
                NewKindActivity.startActivity(getCurrentActivity());
                break;
        }
        mGamDialogFragment.dismiss();
    }
}
