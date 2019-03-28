package com.example.pby.gam_study.page.profile.presenter;

import android.view.View;
import android.widget.Button;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

public class FollowPresenter extends Presenter {

    @BindView(R.id.follow)
    Button mFollowButton;


    @OnClick(R.id.follow)
    public void onFollowClick(View view) {

    }
}
