package com.example.pby.gam_study.page.editImage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.DialogFactory;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.widget.EraserImageView;

import butterknife.BindView;
import butterknife.OnClick;

@Module(EditImageFragment.Context.class)
public class EditImagePresenter extends Presenter {

    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.right_icon)
    ImageView mRightView;
    @BindView(R.id.title)
    TextView mTextView;
    @BindView(R.id.edit_image)
    EraserImageView mEraserImageView;

    @Inject(AccessIds.URL)
    String mUrl;

    private GamDialogFragment mLoadDialog;

    @Override
    protected void onCreate() {
        mLoadDialog = DialogFactory.createLoadDialog(getCurrentActivity());
    }

    @Override
    protected void onBind() {
        mTextView.setText(getString(R.string.title_edit_image));
        mLeftView.setImageDrawable(getDrawable(R.mipmap.icon_back));
        mRightView.setImageDrawable(getDrawable(R.mipmap.icon_ok));
        Glide.with(getCurrentFragment()).asBitmap().load(mUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>() {
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
