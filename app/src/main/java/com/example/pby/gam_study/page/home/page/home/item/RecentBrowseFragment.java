package com.example.pby.gam_study.page.home.page.home.item;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.home.page.home.adapter.KindAdapter;
import com.example.pby.gam_study.page.home.page.home.request.RecentBrowserKindRequest;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RecentBrowseFragment extends RefreshRecyclerViewFragment {

    public static RecentBrowseFragment newInstance() {
        return new RecentBrowseFragment();
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new KindAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createGridLayoutManagerIfEmpty(requireContext(),
                getRecyclerAdapter(), 3, LayoutManagerFactory.DEFAULT_SPAN_FULL);
    }

    @Override
    public Request onCreateRequest() {
        return new RecentBrowserKindRequest();
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }
}
