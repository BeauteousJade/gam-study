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
import com.example.pby.gam_study.page.userlist.adapter.FollowUserListAdapter;
import com.example.pby.gam_study.page.userlist.presenter.fragment.FollowUserListTitleBarPresenter;
import com.example.pby.gam_study.page.userlist.request.FollowUserListRequest;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

public class FollowUserListFragment extends RefreshRecyclerViewFragment {

    private String mUserId;

    public static FollowUserListFragment newInstance(String userId) {
        FollowUserListFragment followUserListFragment = new FollowUserListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UserListActivity.USER_ID, userId);
        followUserListFragment.setArguments(bundle);
        return followUserListFragment;
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
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new FollowUserListTitleBarPresenter());
        return presenter;
    }

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        putExtra(AccessIds.USER_ID, mUserId);
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new LinearLayoutManagerVerticalItemDecoration(0, ResourcesUtil.getColor(requireContext(), R.color.bg_color)
                , DisplayUtil.dpToPx(requireContext(), 2)));
    }

    @Override
    public Request onCreateRequest() {
        return new FollowUserListRequest(mUserId);
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new FollowUserListAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }
}
