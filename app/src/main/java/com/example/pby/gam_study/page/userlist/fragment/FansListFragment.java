package com.example.pby.gam_study.page.userlist.fragment;

import android.os.Bundle;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.LinearLayoutManagerVerticalItemDecoration;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.userlist.UserListActivity;
import com.example.pby.gam_study.page.userlist.adapter.FansListAdapter;
import com.example.pby.gam_study.page.userlist.presenter.fragment.FansListPresenter;
import com.example.pby.gam_study.page.userlist.request.FansListRequest;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

public class FansListFragment extends RefreshRecyclerViewFragment {

    private String mUserId;

    public static FansListFragment newInstance(String userId) {
        FansListFragment fansFragment = new FansListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UserListActivity.USER_ID, userId);
        fansFragment.setArguments(bundle);
        return fansFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_title;
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mUserId = Objects.requireNonNull(getArguments()).getString(UserListActivity.USER_ID);
    }

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        putExtra(AccessIds.USER_ID, mUserId);
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new FansListPresenter());
        return presenter;
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new LinearLayoutManagerVerticalItemDecoration(0, ResourcesUtil.getColor(requireContext(), R.color.bg_color)
                , DisplayUtil.dpToPx(requireContext(), 2)));
    }

    @Override
    public Request onCreateRequest() {
        return new FansListRequest(mUserId);
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new FansListAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }
}
