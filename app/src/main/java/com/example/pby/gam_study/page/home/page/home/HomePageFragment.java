package com.example.pby.gam_study.page.home.page.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.home.page.HomePage;
import com.example.pby.gam_study.page.home.page.home.item.AllKindFragment;
import com.example.pby.gam_study.page.home.page.home.item.DailyTaskFragment;
import com.example.pby.gam_study.page.home.page.home.item.RecentBrowseFragment;
import com.example.pby.gam_study.page.home.page.home.presenter.TitleBarPresenter;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.widget.PageIndicator;
import com.example.pby.gam_study.widget.PagerRecyclerView;
import com.example.pby.gam_study.widget.TitleBar;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class HomePageFragment extends RecyclerViewFragment implements HomePage {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.title_list)
    PageIndicator mRecyclerViewTitle;

    private final PageIndicator.OnItemClickListener mOnItemClickListener = position -> getRecyclerView().smoothScrollToPosition(position);
    private final PagerRecyclerView.OnPageScrollListener mOnPageScrollListener = new PagerRecyclerView.OnPageScrollListener() {

        @Override
        public void onPageSelected(RecyclerView recyclerView, int position) {
            PageIndicator.PageAdapter adapter = (PageIndicator.PageAdapter) mRecyclerViewTitle.getAdapter();
            if (adapter != null) {
                adapter.setSelect(true, position);
            }
        }
    };

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        mRecyclerViewTitle.setTitleList(Arrays.asList(
                new PageIndicator.TitleBean(ResourcesUtil.getString(requireContext(), R.string.title_recent), true),
                new PageIndicator.TitleBean(ResourcesUtil.getString(requireContext(), R.string.title_task)),
                new PageIndicator.TitleBean(ResourcesUtil.getString(requireContext(), R.string.title_all))));
        mRecyclerViewTitle.setRecyclerView(getRecyclerView());
        mRecyclerViewTitle.setSelectedDrawable(R.drawable.bg_title_item_selected);
        mRecyclerViewTitle.setAnchorViewId(R.id.title);
        mRecyclerViewTitle.setHorizontalPadding(DisplayUtil.dpToPx(requireActivity(), 8));
        mRecyclerViewTitle.setVerticalPadding(DisplayUtil.dpToPx(requireActivity(), 2));
        mRecyclerViewTitle.setOnItemClickListener(mOnItemClickListener);

        ((PagerRecyclerView) getRecyclerView()).setOnPageScrollListener(mOnPageScrollListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new TitleBarPresenter());
        return presenter;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new HomePageFragmentAdapter(Arrays.asList(RecentBrowseFragment.newInstance(),
                DailyTaskFragment.newInstance(), AllKindFragment.newInstance()));
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public String getTitle() {
        return ResourcesUtil.getString(requireContext(), R.string.title_home);
    }

    @Override
    public int getRightIcon() {
        return R.drawable.add;
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
