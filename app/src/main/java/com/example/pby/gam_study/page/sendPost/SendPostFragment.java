package com.example.pby.gam_study.page.sendPost;

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

import androidx.recyclerview.widget.RecyclerView;

public class SendPostFragment extends RecyclerViewFragment {

    public static SendPostFragment newInstance() {
        return new SendPostFragment();
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
        presenter.add(new PostContentPresenter());
        presenter.add(new SendPostTitleBarPresenter());
        return presenter;
    }
}
