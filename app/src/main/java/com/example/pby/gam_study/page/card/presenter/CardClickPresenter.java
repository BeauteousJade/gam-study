package com.example.pby.gam_study.page.card.presenter;

import android.view.View;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.card.CardAdapter;
import com.example.pby.gam_study.page.cardDetail.CardDetailActivity;

import butterknife.OnClick;

@Module(CardAdapter.Context.class)
public class CardClickPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Card mCard;


    @OnClick(R.id.image)
    public void onImageClick(View view) {
        CardDetailActivity.startActivity(getCurrentActivity(), mCard);
    }
}
