package com.example.pby.gam_study.page.home.page.news.item;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.home.page.news.adapter.NewsAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class FollowFragment extends RefreshRecyclerViewFragment {


    public static FollowFragment newInstance() {
        return new FollowFragment();
    }

    @Override
    public Request onCreateRequest() {
        return null;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new NewsAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }
}
