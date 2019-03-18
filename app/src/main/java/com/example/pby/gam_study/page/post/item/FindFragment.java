package com.example.pby.gam_study.page.post.item;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;
import com.example.pby.gam_study.page.post.presenter.PostInputPresenter;
import com.example.pby.gam_study.page.post.request.FindRequest;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class FindFragment extends RefreshRecyclerViewFragment {

    public static FindFragment newInstance() {
        return new FindFragment();
    }


    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        getRecyclerView().setItemAnimator(null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_item;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new PostInputPresenter());
        return presenter;
    }

    @Override
    public Request onCreateRequest() {
        return new FindRequest();
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new PostAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }
}