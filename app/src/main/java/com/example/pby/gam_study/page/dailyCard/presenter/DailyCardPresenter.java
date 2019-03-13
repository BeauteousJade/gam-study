package com.example.pby.gam_study.page.dailyCard.presenter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.dailyCard.DailyCardAdapter;

import butterknife.BindView;

@Module(DailyCardAdapter.Context.class)
public class DailyCardPresenter extends Presenter {

    @BindView(R.id.image)
    ImageView mImageView;

    @Inject(AccessIds.ITEM_DATA)
    Card mCard;

    @Override
    protected void onBind() {
        Glide.with(getCurrentFragment()).asDrawable().load(mCard.getEditImageUrl()).into(mImageView);
    }
}