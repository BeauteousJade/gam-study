package com.example.pby.gam_study.page.editCard.presenter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.RequestCode;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.editCard.EditCardFragment;
import com.example.pby.gam_study.page.editImage.EditImageActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoadImagePresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    String mUrl;
    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    @BindView(R.id.image)
    ImageView mImageView;

    private String mNewUrl = null;

    private final BaseActivity.OnActivityResultListener mOnActivityResultListener = (requestCode, resultCode, data) -> {
        if (requestCode == RequestCode.REQUEST_EDIT_IMAGE && resultCode == Activity.RESULT_OK) {
            mNewUrl = data != null ? data.getStringExtra(EditImageActivity.IMAGE_URL) : null;
            if (mNewUrl != null) {
                GlideApp.with(getCurrentFragment()).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).load(mNewUrl).into(mImageView);
                mObservable.notifyChanged(EditCardFragment.KEY_OBSERVABLE_EDIT_IMAGE, mNewUrl);
                return true;
            }
        }
        return false;
    };


    @Override
    protected void onBind() {
        GlideApp.with(getCurrentFragment()).asBitmap().load(mUrl).into(mImageView);
        getCurrentActivity().addOnActivityResultListener(mOnActivityResultListener);
    }

    @Override
    protected void onUnBind() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
    }

    @OnClick(R.id.image)
    public void onImageClick(View view) {
        if (mNewUrl == null) {
            EditImageActivity.startActivityForResult(getCurrentActivity(), mUrl);
        } else {
            EditImageActivity.startActivityForResult(getCurrentActivity(), mNewUrl);
        }
    }
}
