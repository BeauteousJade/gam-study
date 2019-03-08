package com.example.pby.gam_study.page.cardDetail.presenter;

import android.view.View;
import android.widget.ImageView;

import com.example.annation.Module;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.cardDetail.CardDetailFragment;

import butterknife.BindView;
import butterknife.OnClick;

@Module(CardDetailFragment.Context.class)
public class CardDetailPresenter extends Presenter {

    @BindView(R.id.title_bar)
    View mTitleBarView;

    @BindView(R.id.left_icon)
    ImageView mLeftView;

    @Override
    protected void onBind() {
        mTitleBarView.setBackground(null);
        mLeftView.setImageResource(R.mipmap.icon_back);
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }
}
