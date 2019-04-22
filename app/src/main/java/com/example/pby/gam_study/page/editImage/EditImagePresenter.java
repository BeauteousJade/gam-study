package com.example.pby.gam_study.page.editImage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blade.annotation.Inject;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.DialogFactory;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.widget.EraserImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class EditImagePresenter extends Presenter {

    @BindView(R.id.left_icon)
    public ImageView mLeftView;
    @BindView(R.id.right_icon)
    public ImageView mRightView;
    @BindView(R.id.title)
    TextView mTextView;
    @BindView(R.id.edit_image)
    EraserImageView mEraserImageView;

    @Inject(AccessIds.URL)
    public String mUrl;

    private GamDialogFragment mLoadDialog;

    @Override
    protected void onCreate() {
        mLoadDialog = DialogFactory.createLoadDialog(getCurrentActivity());
    }

    @Override
    protected void onBind() {
        mTextView.setText(getString(R.string.title_edit_image));
        mLeftView.setImageDrawable(getDrawable(R.drawable.bg_back));
        mRightView.setImageDrawable(getDrawable(R.drawable.bg_ok));
        GlideApp.with(getCurrentFragment()).asBitmap().load(mUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                mEraserImageView.setBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }

    @OnClick(R.id.right_icon)
    public void onRightClick(View view) {
        mLoadDialog.show(getCurrentFragment().getChildFragmentManager(), "");
        mEraserImageView.save((result, path) -> {
            if (result) {
                Intent intent = new Intent();
                intent.putExtra(EditImageActivity.IMAGE_URL, path);
                getCurrentActivity().setResult(Activity.RESULT_OK, intent);
            } else {
                getCurrentActivity().setResult(Activity.RESULT_CANCELED, null);
            }
            mLoadDialog.dismiss();
            getCurrentActivity().finish();
        });
    }
}
