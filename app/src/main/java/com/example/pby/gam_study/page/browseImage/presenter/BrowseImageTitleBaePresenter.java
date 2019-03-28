package com.example.pby.gam_study.page.browseImage.presenter;

import android.view.View;
import android.widget.ImageView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

public class BrowseImageTitleBaePresenter extends Presenter {

    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.title_bar)
    View mTitleBar;

    @Override
    protected void onBind() {
        mTitleBar.setBackground(null);
        mLeftView.setImageResource(R.drawable.bg_back);
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }
}
