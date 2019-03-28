package com.example.pby.gam_study.page.post.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.sendPost.SendPostActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PostTitleBarPresenter extends Presenter implements View.OnClickListener {

    @Inject
    String mEmpty = null;


    @BindView(R.id.right_icon)
    ImageView mRightView;
    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.title)
    TextView mTitleView;
    private GamDialogFragment mDialog;

    @Override
    protected void onBind() {
        mLeftView.setImageDrawable(getDrawable(R.drawable.bg_back));
        mRightView.setImageDrawable(getDrawable(R.drawable.bg_add));
        mTitleView.setText(getString(R.string.post));
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

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
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
