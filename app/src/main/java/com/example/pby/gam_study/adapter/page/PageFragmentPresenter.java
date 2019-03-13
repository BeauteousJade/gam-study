package com.example.pby.gam_study.adapter.page;


import android.view.View;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.mvp.Presenter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

@Module(PageFragmentAdapter.Context.class)
public class PageFragmentPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    BaseFragment mPageFragment;
    @Inject(AccessIds.ITEM_VIEW)
    View mItemFragmentContainer;


    private final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {
            final int id = mItemFragmentContainer.getId();
            final FragmentManager fragmentManager = getCurrentFragment().getChildFragmentManager();
            final Fragment fragment = fragmentManager.findFragmentById(id);
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss();
            }
            fragmentManager.beginTransaction().replace(id, mPageFragment).commitAllowingStateLoss();
        }

        @Override
        public void onViewDetachedFromWindow(View v) {

        }
    };

    @Override
    protected void onBind() {
        mItemFragmentContainer.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
    }

    @Override
    protected void onUnBind() {
        mItemFragmentContainer.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);
    }
}
