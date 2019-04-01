package com.example.pby.gam_study.page.post.presenter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.GlideFactory;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.util.TimeUtil;

import butterknife.BindView;

public class PostUserPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;


    @BindView(R.id.avatar)
    ImageView mAvatarView;
    @BindView(R.id.userName)
    TextView mUserNameView;
    @BindView(R.id.time)
    TextView mTimeView;

    @Override
    protected void onBind() {
        final User user = mPost.getUser();
        Glide.with(getCurrentFragment())
                .asBitmap()
                .apply(GlideFactory.createCircleOption())
                .load(user.getHead())
                .into(mAvatarView);
        mUserNameView.setText(user.getNickName());
        mTimeView.setText(TimeUtil.formatTime(mPost.getTime()));
    }
}
