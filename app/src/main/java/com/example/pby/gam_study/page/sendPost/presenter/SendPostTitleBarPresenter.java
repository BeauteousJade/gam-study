package com.example.pby.gam_study.page.sendPost.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annation.Module;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.sendPost.SendPostFragment;

import butterknife.BindView;
import butterknife.OnClick;

@Module(SendPostFragment.Context.class)
public class SendPostTitleBarPresenter extends Presenter {

    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.right_icon)
    ImageView mRightView;

    @Override
    protected void onBind() {
        mLeftView.setImageDrawable(getDrawable(R.drawable.bg_back));
        mRightView.setImageDrawable(getDrawable(R.drawable.bg_ok));
        mTitleView.setText(getString(R.string.title_send_post));
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }

    @OnClick(R.id.right_icon)
    public void onRightClick(View view) {

    }
}
