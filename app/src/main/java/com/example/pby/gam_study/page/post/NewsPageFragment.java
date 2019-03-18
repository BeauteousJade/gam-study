package com.example.pby.gam_study.page.post;

import android.os.Bundle;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.page.PageFragmentAdapter;
import com.example.pby.gam_study.fragment.ViewPager2Fragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.post.item.FindFragment;
import com.example.pby.gam_study.page.post.item.FollowFragment;
import com.example.pby.gam_study.page.post.presenter.PostTitleBarPresenter;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.widget.PageIndicator;
import com.example.pby.gam_study.widget.TitleBar;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import butterknife.BindView;

public class NewsPageFragment extends ViewPager2Fragment {

    public static final String KEY_EXPRESSION_CLICK = "key_expression_click";
    public static final String KEY_ADD_COMMENT = "key_add_comment";


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
        presenter.add(new PostTitleBarPresenter());
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_page;
    }
}