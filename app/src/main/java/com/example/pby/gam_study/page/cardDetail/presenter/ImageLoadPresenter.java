package com.example.pby.gam_study.page.cardDetail.presenter;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.widget.ZoomImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;

public class ImageLoadPresenter extends Presenter {

    @Inject(AccessIds.CARD)
    public Card mCard;

    @BindView(R.id.image)
    public ZoomImageView mZoomImageView;

    @Override
    protected void onBind() {
        GlideApp.with(getCurrentFragment()).asDrawable().load(mCard.getEditImageUrl()).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mZoomImageView.setDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }
}
