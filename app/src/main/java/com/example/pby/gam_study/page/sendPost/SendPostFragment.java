package com.example.pby.gam_study.page.sendPost;

import android.os.Bundle;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.PostImageDecoration;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.sendPost.presenter.ExpressionPresenter;
import com.example.pby.gam_study.page.sendPost.presenter.PostContentPresenter;
import com.example.pby.gam_study.page.sendPost.presenter.SendPostTitleBarPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

public class SendPostFragment extends RecyclerViewFragment {

    private String mPostContent;

    public static SendPostFragment newInstance(String postContent) {
        SendPostFragment sendPostFragment = new SendPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SendPostActivity.POST_CONTENT, postContent);
        sendPostFragment.setArguments(bundle);
        return sendPostFragment;
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mPostContent = Objects.requireNonNull(getArguments()).getString(SendPostActivity.POST_CONTENT);
    }

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        putExtra(AccessIds.POST_CONTENT, mPostContent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_send_post;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        List<Object> dataList = new ArrayList<>();
        dataList.add(R.mipmap.icon_album);
        return new ImageContainerAdapter(dataList);
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new PostImageDecoration(requireContext()));
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createHorizontalLayoutManager(requireContext());
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new ExpressionPresenter());
        presenter.add(new SendPostTitleBarPresenter());
        presenter.add(new PostContentPresenter());
        return presenter;
    }
}
