package com.example.pby.gam_study.page.home.page.news.presenter;

import android.view.View;

import com.example.annation.Module;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.home.page.news.NewsPageFragment;
import com.example.pby.gam_study.page.sendPost.SendPostActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Module(NewsPageFragment.Context.class)
public class NewsTitleBarPresenter extends Presenter implements View.OnClickListener {

    @BindView(R.id.right_icon)
    View mRightView;
    private GamDialogFragment mDialog;

    @Override
    protected void onBind() {

    }

    @OnClick(R.id.right_icon)
    public void onRightClick(View view) {
        if (mDialog == null) {
            mDialog = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_RIGHT_BOTTOM, R.layout.menu_send_post)
                    .setAnchorView(mRightView)
                    .setOnViewClickListener(this, R.id.send_post)
                    .build();
        }
        mDialog.show(getCurrentFragment().getChildFragmentManager(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_post:
                SendPostActivity.startActivity(getCurrentActivity());
                break;
        }
        mDialog.dismiss();
    }
}
