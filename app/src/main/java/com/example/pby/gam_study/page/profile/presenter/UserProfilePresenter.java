package com.example.pby.gam_study.page.profile.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.profile.UserProfileFragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

@Module(UserProfileFragment.Context.class)
public class UserProfilePresenter extends Presenter {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.title_bar)
    View mTitleBar;
    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.left_icon)
    ImageView mLeftIcon;

    @Inject(AccessIds.USER)
    User mUser;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            mTitleBar.setAlpha(Math.min(dy / 100.f, 1));
        }
    };

    @Override
    protected void onBind() {
        mTitleBar.setAlpha(0);
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mTitleView.setText(String.format(getString(R.string.regex_user_profile), mUser.getNickName()));
        mLeftIcon.setImageDrawable(getDrawable(R.drawable.bg_back));
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        if (view.getAlpha() > 0) {
            getCurrentActivity().finish();
        }
    }

    @Override
    protected void onDestroy() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
    }
}
