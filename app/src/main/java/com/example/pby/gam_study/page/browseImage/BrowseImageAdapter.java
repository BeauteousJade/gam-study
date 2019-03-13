package com.example.pby.gam_study.page.browseImage;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.browseImage.presenter.ImageLoadPresenter;

import java.util.List;

public class BrowseImageAdapter extends BaseRecyclerAdapter<String> {
    public BrowseImageAdapter(List<String> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_browse_image;
    }

    @Override
    protected Presenter onCreatePresenter(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new ImageLoadPresenter());
        return presenter;
    }
}
