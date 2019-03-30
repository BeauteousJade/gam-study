package com.example.pby.gam_study.page.profile.presenter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.GlideFactory;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;

import butterknife.BindView;

public class UserHeadPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    User mUser;

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.nick_name)
    TextView mNickName;
    @BindView(R.id.fans_count)
    TextView mFansCount;
    @BindView(R.id.follow_count)
    TextView mFollowCount;

    @Override
    protected void onBind() {
        Glide.with(getCurrentFragment()).asBitmap().apply(GlideFactory.createCircleOption()).load(mUser.getHead()).into(mAvatar);
        mNickName.setText(mUser.getNickName());
        mFansCount.setText(String.format(getString(R.string.regex_fans_count), formatCount(mUser.getFansUserList().size())));
        mFollowCount.setText(String.format(getString(R.string.regex_follow_count), formatCount(mUser.getFollowUserList().size())));
    }

    private String formatCount(int size) {
        if (size < 1000) {
            return String.valueOf(size);
        } else if (size < 10000) {
            return String.valueOf(size / 1000 + "k");
        } else {
            return String.valueOf(size / 10000 + "w");
        }
    }
}
