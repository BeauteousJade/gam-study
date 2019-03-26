package com.example.pby.gam_study.page.post.presenter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.GlideFactory;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;
import com.example.pby.gam_study.util.TimeUtil;

import butterknife.BindView;

@Module(PostAdapter.Context.class)
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
        mTimeView.setText(getTime());
    }

    private String getTime() {
        final long time1 = mPost.getTime();
        final long time2 = System.currentTimeMillis();
        final int dDay = TimeUtil.differentDays(time1, time2);
        final int dYear = TimeUtil.differentYears(time1, time2);
        if (dDay == 0) {
            return TimeUtil.formatTime(time1, "今天 HH:mm");
        } else if (dDay == 1) {
            return TimeUtil.formatTime(time1, "昨天 HH:mm");
        } else if (dDay == 2) {
            return TimeUtil.formatTime(time1, "前天 HH:mm");
        } else if (dYear == 0) {
            return TimeUtil.formatTime(time1, "MM:dd HH:mm");
        } else {
            return TimeUtil.formatTime(time1, "yyyy:MM:dd");
        }
    }

}
