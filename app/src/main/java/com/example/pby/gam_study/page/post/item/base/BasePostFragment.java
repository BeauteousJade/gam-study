package com.example.pby.gam_study.page.post.item.base;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.LinearLayoutManagerVerticalItemDecoration;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.post.PostLinearLayoutManager;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;
import com.example.pby.gam_study.presenter.InputPresenter;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BasePostFragment extends RefreshRecyclerViewFragment {

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
        presenter.add(new InputPresenter());
        return presenter;
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new LinearLayoutManagerVerticalItemDecoration(0, ResourcesUtil.getColor(requireContext(), R.color.bg_color),
                DisplayUtil.dpToPx(requireContext(), 20)));
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new PostAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new PostLinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
    }
}
