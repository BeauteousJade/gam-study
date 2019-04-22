package com.example.pby.gam_study.fragment;

import android.os.Bundle;
import android.view.View;

import com.blade.annotation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.widget.viewpager2.ViewPager2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import butterknife.BindView;

public abstract class ViewPager2Fragment extends BaseFragment {

    @BindView(R.id.viewPager2)
    protected ViewPager2 mViewPager2;
    private FragmentStateAdapter mAdapter;

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        mAdapter = onCreateAdapter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager2.setAdapter(mAdapter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mContext = super.onCreateBaseContext();
        context.mAdapter = mAdapter;
        return (T) context;
    }

    protected abstract FragmentStateAdapter onCreateAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_viewpager2;
    }

    public static class Context {
        @Provides(deepProvides = true)
        public BaseFragment.Context mContext;
        @Provides(AccessIds.RECYCLER_ADAPTER)
        public FragmentStateAdapter mAdapter;
    }
}
