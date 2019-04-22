package com.example.pby.gam_study.page.sendPost.presenter;

import android.view.View;
import android.widget.ImageView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.adapter.base.BaseViewHolder;
import com.example.pby.gam_study.mvp.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

public class PostImagePresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    public Object url;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    public BaseRecyclerAdapter<Object> mAdapter;
    @Inject(AccessIds.VIEW_HOLDER)
    public BaseViewHolder mViewHolder;

    @BindView(R.id.image)
    public ImageView mImageView;

    @Override
    protected void onBind() {
        GlideApp.with(getCurrentFragment()).asBitmap().load(url).into(mImageView);
    }

    @OnClick(R.id.close)
    public void onCloseClick(View view) {
        mAdapter.remove(mViewHolder.getAdapterPosition());
        if (mAdapter.getItemCount() < 5 && !(mAdapter.getItem(mAdapter.getItemCount() - 1) instanceof Integer)) {
            mAdapter.addItem(R.mipmap.icon_album);
        }
    }
}
