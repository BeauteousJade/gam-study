package com.example.pby.gam_study.page.card.presenter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.card.CardAdapter;

import butterknife.BindView;

@Module(CardAdapter.Context.class)
public class CardPresenter extends Presenter {

    @BindView(R.id.image)
    ImageView mImageView;

    @Inject(AccessIds.ITEM_DATA)
    Card mCard;

    @Override
    protected void onBind() {
        GlideApp.with(getCurrentFragment()).asDrawable().load(mCard.getEditImageUrl()).into(mImageView);
    }
}
