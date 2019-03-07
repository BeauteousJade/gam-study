package com.example.pby.gam_study.page.newCard.presenter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.RequestCode;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.editImage.EditImageActivity;
import com.example.pby.gam_study.page.newCard.NewCardFragment;

import butterknife.BindView;
import butterknife.OnClick;

@Module(BaseRecyclerAdapter.Context.class)
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
                Glide.with(getCurrentFragment()).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).load(mNewUrl).into(mImageView);
                mObservable.notifyChanged(NewCardFragment.KEY_OBSERVABLE_EDIT_IMAGE, mNewUrl);
            }
        }
        return false;
    };


    @Override
    protected void onBind() {
        Glide.with(getCurrentFragment()).asBitmap().load(mUrl).into(mImageView);
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