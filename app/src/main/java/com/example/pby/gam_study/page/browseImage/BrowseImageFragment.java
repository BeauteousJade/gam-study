package com.example.pby.gam_study.page.browseImage;

import android.os.Bundle;


import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.browseImage.presenter.BrowseImageTitleBaePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class BrowseImageFragment extends RecyclerViewFragment {


    private List<String> mImageUrlList;

    public static BrowseImageFragment newInstance(ArrayList<String> imageUrlList) {
        BrowseImageFragment browseImageFragment = new BrowseImageFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(BrowseImageActivity.IMAGE_URL_LIST, imageUrlList);
        browseImageFragment.setArguments(bundle);
        return browseImageFragment;
    }

    @Override
    protected void onPrepare() {
        mImageUrlList = Objects.requireNonNull(getArguments()).getStringArrayList(BrowseImageActivity.IMAGE_URL_LIST);
    }


    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        getRecyclerView().setItemAnimator(null);
    }


    @Override
    protected PagerSnapHelper onCreateSnapHelper() {
        return new PagerSnapHelper();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview_title;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new BrowseImageAdapter(mImageUrlList == null ? Collections.emptyList() : mImageUrlList);
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new BrowseImageTitleBaePresenter());
        return presenter;
    }
}
