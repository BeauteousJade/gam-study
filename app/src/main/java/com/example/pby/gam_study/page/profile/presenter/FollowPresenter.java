package com.example.pby.gam_study.page.profile.presenter;

import android.view.View;
import android.widget.Button;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.profile.request.FollowRequest;
import com.example.pby.gam_study.page.profile.request.UnFollowRequest;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class FollowPresenter extends Presenter {

    @BindView(R.id.follow)
    Button mFollowButton;

    @Inject(AccessIds.ITEM_DATA)
    User mUser;
    @Inject(AccessIds.ITEM_POSITION)
    int mPosition;

    private FollowRequest mFollowRequest;
    private UnFollowRequest mUnFollowRequest;


    @Override
    protected void onBind() {
        if (Objects.equals(mUser.getId(), LoginManager.getCurrentUser().getId())) {
            mFollowButton.setVisibility(View.INVISIBLE);
        }
        if (mUser.getIsFollow() == 1) {
            mFollowButton.setText(getString(R.string.followed));
            mFollowButton.setSelected(true);
        } else {
            mFollowButton.setText(getString(R.string.follow));
            mFollowButton.setSelected(false);
        }
    }

    @OnClick(R.id.follow)
    public void onFollowClick(View view) {
        if (mFollowRequest != null) {
            mFollowRequest.cancel();
        }
        if (mUnFollowRequest != null) {
            mUnFollowRequest.cancel();
        }
        if (mFollowButton.isSelected()) {
            mUnFollowRequest = new UnFollowRequest(LoginManager.getCurrentUser().getId(), mUser.getId());
            mUnFollowRequest.enqueue();
            mFollowButton.setText(getString(R.string.follow));
        } else {
            mFollowRequest = new FollowRequest(LoginManager.getCurrentUser().getId(), mUser.getId());
            mFollowRequest.enqueue();
            mFollowButton.setText(getString(R.string.followed));
        }
        mFollowButton.setSelected(!mFollowButton.isSelected());
    }
}
