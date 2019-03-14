package com.example.pby.gam_study.page.sendPost;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.sendPost.presenter.EmptyImageClickPresenter;
import com.example.pby.gam_study.page.sendPost.presenter.PostImagePresenter;

import java.util.List;

public class ImageContainerAdapter extends BaseRecyclerAdapter<Object> {

    private static final int TYPE_ALBUM = 1;
    private static final int TYPE_IMAGE = 2;

    public ImageContainerAdapter(List<Object> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        switch (viewType) {
            case TYPE_IMAGE:
                return R.layout.item_image_post;
            default:
                return R.layout.item_image_post_empty;
        }
    }


    @Override
    public int getItemViewTypeNoEmpty(int position) {
        if (position == getItemCount() - 1 && mDataList.get(position) instanceof Integer) {
            return TYPE_ALBUM;
        }
        return TYPE_IMAGE;
    }

    @Override
    protected Presenter onCreatePresenter(int viewType) {
        Presenter presenter = new Presenter();
        switch (viewType) {
            case TYPE_IMAGE:
                presenter.add(new PostImagePresenter());
                break;
            case TYPE_ALBUM:
                presenter.add(new EmptyImageClickPresenter());
                break;
        }
        return presenter;
    }
}
