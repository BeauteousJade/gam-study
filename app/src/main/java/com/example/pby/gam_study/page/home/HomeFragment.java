package com.example.pby.gam_study.page.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.fragment.tabhost.TabHost;
import com.example.pby.gam_study.page.home.page.home.HomePageFragment;
import com.example.pby.gam_study.page.home.page.message.MessagePageFragment;
import com.example.pby.gam_study.page.home.page.mine.MinePageFragment;
import com.example.pby.gam_study.page.home.page.news.NewsPageFragment;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.widget.item.ItemLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements ItemLayout.OnItemClickListener {
    private final List<BaseFragment> mPageFragments = Arrays.asList(
            HomePageFragment.newInstance(), NewsPageFragment.newInstance(),
            MessagePageFragment.newInstance(), MinePageFragment.newInstance());

    @BindView(R.id.tab)
    ItemLayout mTabLayout;
    @BindView(R.id.fragment_container)
    View mFragmentContainer;

    private BaseFragment mCurPageFragment;
    private int mCurPosition;
    private ItemLayout.Adapter mTabAdapter;
    private List<HomeTab> mHomeTabs = new ArrayList<>();

    private final FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        @Override
        public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
            if (f.getClass() == NewsPageFragment.class) {
                onItemClick(0);
                fm.unregisterFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks);
            }
        }
    };

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout.setOnTabClickListener(this);
        mTabLayout.setAdapter(mTabAdapter);
    }

    @Override
    public void onPrepareBaseContext() {
        mHomeTabs.add(new HomeTab(ResourcesUtil.getString(requireContext(), R.string.title_home), R.mipmap.icon_page_home_unselected, R.mipmap.icon_page_home_selected));
        mHomeTabs.add(new HomeTab(ResourcesUtil.getString(requireContext(), R.string.title_news), R.mipmap.icon_page_news_unselected, R.mipmap.icon_page_news_selected));
        mHomeTabs.add(new HomeTab(ResourcesUtil.getString(requireContext(), R.string.title_message), R.mipmap.icon_page_message_unselected, R.mipmap.icon_page_message_selected));
        mHomeTabs.add(new HomeTab(ResourcesUtil.getString(requireContext(), R.string.title_mine), R.mipmap.icon_page_mine_unselected, R.mipmap.icon_page_mine_selected));
        mTabAdapter = new HomeTabAdapter(mHomeTabs);


        final int id = mFragmentContainer.getId();
        final FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (BaseFragment pageFragment : mPageFragments) {
            transaction.add(id, pageFragment);
            transaction.hide(pageFragment);
        }
        transaction.commitAllowingStateLoss();
        fragmentManager.unregisterFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks);
        fragmentManager.registerFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks, false);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onItemClick(int position) {
        final BaseFragment prePageFragment = mCurPageFragment;
        mCurPageFragment = mPageFragments.get(position);
        if (mCurPageFragment == prePageFragment) {
            return;
        }
        final FragmentManager fragmentManager = getChildFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (prePageFragment instanceof TabHost) {
            transaction.hide(prePageFragment);
            ((TabHost) prePageFragment).onPageUnSelect();
        }
        transaction.show(mCurPageFragment).commitNowAllowingStateLoss();
        if (mCurPageFragment instanceof TabHost) {
            ((TabHost) mCurPageFragment).onPageSelect();
        }

        final int prePosition = mCurPosition;
        mCurPosition = position;
        mHomeTabs.get(prePosition).setSelected(false);
        mHomeTabs.get(mCurPosition).setSelected(true);
        mTabAdapter.notifyDataSetChanged();
    }
}
