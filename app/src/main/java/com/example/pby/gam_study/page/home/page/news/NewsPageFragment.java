package com.example.pby.gam_study.page.home.page.news;

import android.os.Bundle;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.page.PageFragmentAdapter;
import com.example.pby.gam_study.fragment.ViewPager2Fragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.home.page.BaseHomePageFragment;
import com.example.pby.gam_study.page.home.page.HomePage;
import com.example.pby.gam_study.page.home.page.news.item.FindFragment;
import com.example.pby.gam_study.page.home.page.news.item.FollowFragment;
import com.example.pby.gam_study.page.home.page.news.presenter.NewsTitleBarPresenter;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.widget.PageIndicator;
import com.example.pby.gam_study.widget.TitleBar;
import com.example.pby.gam_study.widget.viewpager2.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import butterknife.BindView;

import static com.example.pby.gam_study.fragment.tabhost.PageFragment.KEY_LAYOUT_ID;

public class NewsPageFragment extends ViewPager2Fragment implements HomePage {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.title_list)
    PageIndicator mRecyclerViewTitle;

    private final PageIndicator.OnItemClickListener mOnItemClickListener = position -> mViewPager2.setCurrentItem(position, true);

    public static NewsPageFragment newInstance() {
        return new NewsPageFragment();
    }


    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        mRecyclerViewTitle.setTitleList(Arrays.asList(
                new PageIndicator.TitleBean(ResourcesUtil.getString(requireContext(), R.string.title_find), true),
                new PageIndicator.TitleBean(ResourcesUtil.getString(requireContext(), R.string.title_follow))));
        mRecyclerViewTitle.setViewPager(mViewPager2);
        mRecyclerViewTitle.setSelectedDrawable(R.drawable.bg_title_item_selected);
        mRecyclerViewTitle.setAnchorViewId(R.id.title);
        mRecyclerViewTitle.setHorizontalPadding(DisplayUtil.dpToPx(requireActivity(), 8));
        mRecyclerViewTitle.setVerticalPadding(DisplayUtil.dpToPx(requireActivity(), 2));
        mRecyclerViewTitle.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected FragmentStateAdapter onCreateAdapter() {
        return new PageFragmentAdapter(getChildFragmentManager(), Arrays.asList(FindFragment.newInstance(), FollowFragment.newInstance()));
    }


    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new NewsTitleBarPresenter());
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_page;
    }

    @Override
    public String getTitle() {
        return ResourcesUtil.getString(requireContext(), R.string.title_news);
    }

    @Override
    public int getRightIcon() {
        return R.drawable.bg_add;
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