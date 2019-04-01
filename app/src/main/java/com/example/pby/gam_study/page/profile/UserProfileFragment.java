package com.example.pby.gam_study.page.profile;

import android.os.Bundle;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.LinearLayoutManagerVerticalItemDecoration;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.post.PostLinearLayoutManager;
import com.example.pby.gam_study.presenter.InputPresenter;
import com.example.pby.gam_study.page.profile.presenter.UserProfilePresenter;
import com.example.pby.gam_study.page.profile.presenter.UserProfileRefreshPresenter;
import com.example.pby.gam_study.page.profile.request.UserProfileRequest;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

public class UserProfileFragment extends RefreshRecyclerViewFragment {

    private User mUser;

    public static UserProfileFragment newInstance(User user) {
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(UserProfileActivity.USER, user);
        userProfileFragment.setArguments(bundle);
        return userProfileFragment;
    }

    @Override
    protected void onPrepare() {
        mUser = Objects.requireNonNull(getArguments()).getParcelable(UserProfileActivity.USER);
        putExtra(AccessIds.USER, mUser);
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new LinearLayoutManagerVerticalItemDecoration(2, ResourcesUtil.getColor(requireContext(), R.color.bg_color),
                DisplayUtil.dpToPx(requireContext(), 20)));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_profile;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new UserProfileRefreshPresenter());
        presenter.add(new UserProfilePresenter());
        presenter.add(new InputPresenter());
        return presenter;
    }

    @Override
    public Request onCreateRequest() {
        return new UserProfileRequest(mUser.getId());
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new UserProfileAdapter(new ArrayList<>(), mUser);
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new PostLinearLayoutManager(requireContext());
    }
}
