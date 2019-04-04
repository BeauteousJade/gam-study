package com.example.pby.gam_study.page.home.page.home.item;

import android.os.Bundle;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.GridItemDecoration;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.home.page.home.adapter.KindAdapter;
import com.example.pby.gam_study.page.home.page.home.request.KindRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

public class AllKindFragment extends RefreshRecyclerViewFragment {

    private static final String USER_ID = "user_id";

    private String mUserId;

    public static AllKindFragment newInstance() {
        return newInstance(LoginManager.getCurrentUser().getId());
    }

    public static AllKindFragment newInstance(String userId) {
        AllKindFragment kindFragment = new AllKindFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USER_ID, userId);
        kindFragment.setArguments(bundle);
        return kindFragment;
    }

    @Override
    protected void onPrepare() {
        mUserId = Objects.requireNonNull(getArguments()).getString(USER_ID);
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new KindAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createGridLayoutManagerIfEmpty(requireContext(), getRecyclerAdapter(), 3, LayoutManagerFactory.DEFAULT_SPAN_FULL);
    }

    @Override
    public Request onCreateRequest() {
        return new KindRequest(mUserId);
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new GridItemDecoration());
    }
}
