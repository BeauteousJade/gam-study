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

    @Inject(AccessIds.USER)
    User mUser;

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.nick_name)
    TextView mNickName;

    @Override
    protected void onBind() {
        Glide.with(getCurrentFragment()).asBitmap().apply(GlideFactory.createCircleOption()).load(mUser.getHead()).into(mAvatar);
        mNickName.setText(mUser.getNickName());
    }
}
