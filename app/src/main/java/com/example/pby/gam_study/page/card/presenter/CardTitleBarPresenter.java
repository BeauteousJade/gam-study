package com.example.pby.gam_study.page.card.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.card.CardFragment;

import butterknife.BindView;
import butterknife.OnClick;

@Module(CardFragment.Context.class)
public class CardTitleBarPresenter extends Presenter {

    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.title)
    TextView mTitleView;
    @Inject(AccessIds.TITLE)
    String mKindName;

    @Override
    protected void onBind() {
        mTitleView.setText(mKindName);
        mLeftView.setImageResource(R.mipmap.icon_back);
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }
}
