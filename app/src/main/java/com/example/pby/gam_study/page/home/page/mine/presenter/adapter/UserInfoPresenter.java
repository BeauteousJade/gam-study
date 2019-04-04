package com.example.pby.gam_study.page.home.page.mine.presenter.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.GlideFactory;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;

import butterknife.BindView;

public class UserInfoPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    User mUser;

    @BindView(R.id.avatar)
    ImageView mAvatarView;
    @BindView(R.id.nick_name)
    TextView mNameView;
    @BindView(R.id.fans_count)
    TextView mFansCountView;
    @BindView(R.id.follow_count)
    TextView mFollowCount;
    @BindView(R.id.score)
    TextView mScoreView;

    @Override
    protected void onBind() {
        GlideApp.with(getCurrentFragment())
                .asBitmap()
                .apply(GlideFactory.createCircleOption())
                .load(mUser.getHead())
                .into(mAvatarView);
        mNameView.setText(mUser.getNickName());
        mFansCountView.setText(String.format(getString(R.string.regex_fans_count), mUser.getFansUserList().size()));

        mFollowCount.setText(String.format(getString(R.string.regex_follow_count), mUser.getFollowUserList().size()));
        mScoreView.setText(String.format(getString(R.string.regex_score_count), mUser.getScore()));

    }

    private boolean isUpdateView(String payload, String value) {
        return payload == null || payload.contains(value);
    }
}
