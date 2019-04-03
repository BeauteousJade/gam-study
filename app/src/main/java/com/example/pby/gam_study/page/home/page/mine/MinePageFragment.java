package com.example.pby.gam_study.page.home.page.mine;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.LinearLayoutManagerVerticalItemDecoration;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.home.page.HomePage;
import com.example.pby.gam_study.page.home.page.mine.presenter.mine.MineRefreshPresenter;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.widget.TitleBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MinePageFragment extends RefreshRecyclerViewFragment implements HomePage {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    private final LinearLayoutManagerVerticalItemDecoration.OnItemOffsetListener mOnItemOffsetListener = position -> {
        if (position > 0 && position < getRecyclerAdapter().getItemCount()) {
            if (getRecyclerAdapter().getItem(position - 1) != null) {
                return true;
            }
        }
        return false;
    };

    public static MinePageFragment newInstance() {
        return new MinePageFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_mine;
    }

    @Override
    public Request onCreateRequest() {
        return new MineRequest();
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new MineAdapter(new ArrayList<>());
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new MineRefreshPresenter());
        return presenter;
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new LinearLayoutManagerVerticalItemDecoration(mOnItemOffsetListener,
                ResourcesUtil.getColor(requireContext(), R.color.bg_color),DisplayUtil.dpToPx(requireContext(), 1)));
    }

    @Override
    public String getTitle() {
        return ResourcesUtil.getString(requireContext(), R.string.title_mine);
    }

    @Override
    public void onPageSelect() {
        mTitleBar.setTitle(getTitle());
        mTitleBar.setLeftIcon(getLeftIcon());
        mTitleBar.setRightIcon(getRightIcon());
    }

    @Override
    public void onPageUnSelect() {

    }
}
