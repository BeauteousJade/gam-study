package com.example.pby.gam_study.page.dailyCard.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.ImageView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.dailyCard.DailyCardAdapter;
import com.example.pby.gam_study.page.dailyCard.UpdateDailyCardRequest;
import com.example.pby.gam_study.widget.layoutManager.SlideItemTouchCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DailyCardSlidePresenter extends Presenter {

    @Inject(AccessIds.CALLBACK)
    SlideItemTouchCallback mCallback;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    DailyCardAdapter mAdapter;

    @BindView(R.id.not_remember)
    ImageView mNotRememberView;
    @BindView(R.id.remember)
    ImageView mRememberView;

    private List<Request> mRequestList = new ArrayList<>();

    private final SlideItemTouchCallback.OnSlideListener mSlideListener = new SlideItemTouchCallback.OnSlideListener() {
        @Override
        public void onSlide(float dx, float dy) {
        }

        @Override
        public void onSlided(boolean right, int position) {
            startAnimation(right ? mRememberView : mNotRememberView);
            if (right) {
                Request<Boolean> request = new UpdateDailyCardRequest(mAdapter.getItem(position).getId());
                request.enqueue();
                mRequestList.add(request);
            }
        }
    };

    @Override
    protected void onBind() {
        mCallback.addOnSlideListener(mSlideListener);
    }

    @Override
    protected void onUnBind() {
        mCallback.removeOnSlideListener(mSlideListener);
        for (Request request : mRequestList) {
            request.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        mCallback.removeOnSlideListener(mSlideListener);
        endAnimation(mRememberView);
        endAnimation(mNotRememberView);
        for (Request request : mRequestList) {
            request.cancel();
        }
    }

    private void startAnimation(View view) {
        endAnimation(view);
        view.animate().scaleX(1.5f).scaleY(1.5f).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                view.animate().setListener(null);
                view.setScaleY(1.5f);
                view.setScaleY(1.5f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.animate().setListener(null);
                view.setScaleY(1.5f);
                view.setScaleY(1.5f);
                view.animate().scaleX(1.f).scaleY(1.f).setDuration(300).start();
            }
        }).start();

    }

    private void endAnimation(View view) {
        view.animate().cancel();
    }
}
