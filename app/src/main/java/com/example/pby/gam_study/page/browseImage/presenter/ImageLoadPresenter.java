package com.example.pby.gam_study.page.browseImage.presenter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.browseImage.BrowseImageAdapter;
import com.example.pby.gam_study.widget.ZoomImageView;

import butterknife.BindView;

@Module(BrowseImageAdapter.Context.class)
public class ImageLoadPresenter extends Presenter {

    @BindView(R.id.image)
    ZoomImageView mZoomImageView;

    @Inject(AccessIds.ITEM_DATA)
    String mUrl;

    @Override
    protected void onBind() {
        Glide.with(getCurrentFragment()).asDrawable().load(mUrl).into(new CustomTarget<Drawable>() {
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
