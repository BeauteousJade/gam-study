package com.example.pby.gam_study.page.profile;

import android.os.Bundle;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.profile.presenter.UserProfilePresenter;

import java.util.ArrayList;
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_profile;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mUser = mUser;
        context.mContext = super.onCreateBaseContext();
        return (T) context;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new UserProfilePresenter());
        return presenter;
    }

    @Override
    public Request onCreateRequest() {
        return null;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        List<Post> postList = new ArrayList<>();
        postList.add(null);
        UserProfileAdapter profileAdapter = new UserProfileAdapter(postList);
        profileAdapter.setUser(mUser);
        return profileAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }


    public static class Context {
        @Provides(AccessIds.USER)
        public User mUser;
        @Provides(deepProvides = true)
        public RefreshRecyclerViewFragment.Context mContext;
    }
}
