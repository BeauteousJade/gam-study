package com.example.pby.gam_study.page.sendPost.presenter;

import android.view.View;
import android.widget.ImageView;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.adapter.base.BaseViewHolder;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.sendPost.ImageContainerAdapter;

import butterknife.BindView;
import butterknife.OnClick;

@Module(ImageContainerAdapter.Context.class)
public class PostImagePresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Object url;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter<Object> mAdapter;
    @Inject(AccessIds.VIEW_HOLDER)
    BaseViewHolder mViewHolder;

    @BindView(R.id.image)
    ImageView mImageView;

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
