package com.example.pby.gam_study.page.card.presenter;

import android.widget.ImageView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;

import butterknife.BindView;

public class CardPresenter extends Presenter {

    @BindView(R.id.image)
    public ImageView mImageView;

    @Inject(AccessIds.ITEM_DATA)
    public Card mCard;

    @Override
    protected void onBind() {
        GlideApp.with(getCurrentFragment()).asDrawable().load(mCard.getEditImageUrl()).into(mImageView);
    }
}
