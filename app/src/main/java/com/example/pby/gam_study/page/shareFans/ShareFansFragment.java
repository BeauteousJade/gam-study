package com.example.pby.gam_study.page.shareFans;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.LinearLayoutManagerVerticalItemDecoration;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.userlist.presenter.fragment.FansListPresenter;
import com.example.pby.gam_study.page.userlist.request.FansListRequest;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ShareFansFragment extends RefreshRecyclerViewFragment {

    public static ShareFansFragment newInstance() {
        return new ShareFansFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_title;
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
        return new FansListRequest(LoginManager.getCurrentUser().getId());
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new ShareFansAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }
}
