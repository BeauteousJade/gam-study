package com.example.pby.gam_study.page.card.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.ImageView;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.card.CardFragment;
import com.example.pby.gam_study.widget.layoutManager.SlideItemTouchCallback;

import butterknife.BindView;

@Module(CardFragment.Context.class)
public class SlideCardPresenter extends Presenter {

    @Inject(AccessIds.CALLBACK)
    SlideItemTouchCallback mCallback;

    @BindView(R.id.not_remember)
    ImageView mNotRememberView;
    @BindView(R.id.remember)
    ImageView mRememberView;


    private final SlideItemTouchCallback.OnSlideListener mSlideListener = new SlideItemTouchCallback.OnSlideListener() {
        @Override
        public void onSlide(float dx, float dy) {
        }

        @Override
        public void onSlided(boolean right, int position) {
            startAnimation(right ? mRememberView : mNotRememberView);
        }
    };

    @Override
    protected void onBind() {
        mCallback.addOnSlideListener(mSlideListener);
    }

    @Override
    protected void onUnBind() {
        mCallback.removeOnSlideListener(mSlideListener);
    }

    @Override
    protected void onDestroy() {
        mCallback.removeOnSlideListener(mSlideListener);
        endAnimation(mRememberView);
        endAnimation(mNotRememberView);
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
        view.animate().setListener(null);
    }
}
