package com.example.pby.gam_study.page.cardDetail.presenter;

import android.view.View;

import com.example.annation.Module;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.cardDetail.CardDetailFragment;
import com.example.pby.gam_study.util.ToastUtil;

import butterknife.OnClick;

@Module(CardDetailFragment.Context.class)
public class BottomBarPresenter extends Presenter {


    @OnClick(R.id.more)
    public void onMoreClick(View view) {
        ToastUtil.info(getCurrentActivity(), "more");
    }
}
