package com.example.pby.gam_study.page.home.page.mine;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.network.request.Request;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MinePageFragment extends RefreshRecyclerViewFragment {

    public static MinePageFragment newInstance() {
        return new MinePageFragment();
    }

    @Override
    public Request onCreateRequest() {
        return null;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new MineAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }
}
