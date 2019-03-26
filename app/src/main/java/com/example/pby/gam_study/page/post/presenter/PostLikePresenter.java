package com.example.pby.gam_study.page.post.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.ImageView;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;
import com.example.pby.gam_study.page.post.request.LikeRequest;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

@Module(PostAdapter.Context.class)
public class PostLikePresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;
    @Inject(AccessIds.VIEW_HOLDER)
    RecyclerView.ViewHolder mViewHolder;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter mAdapter;


    @BindView(R.id.like_operation)
    ImageView mImageView;

    private Request<Boolean> mRequest;

    @Override
    protected void onBind() {
        if (mPost.getLikeUserList().contains(LoginManager.getCurrentUser())) {
            mImageView.setImageDrawable(getDrawable(R.mipmap.icon_liked));
        } else {
            mImageView.setImageDrawable(getDrawable(R.mipmap.icon_unlike));
        }
    }

    @OnClick(R.id.like_operation)
    public void onLikeClick(View view) {
        if (mRequest != null) {
            mRequest.cancel();
        }
        if (mPost.getLikeUserList().contains(LoginManager.getCurrentUser())) {
            mPost.getLikeUserList().remove(LoginManager.getCurrentUser());
            mImageView.setImageDrawable(getDrawable(R.mipmap.icon_unlike));
            mRequest = new LikeRequest(mPost.getId(), false);
            mPost.getLikeUserList().remove(LoginManager.getCurrentUser());
        } else {
            mImageView.setImageDrawable(getDrawable(R.mipmap.icon_liked));
            view.animate().cancel();
            view.animate().scaleY(1.8f).scaleX(1.8f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    view.setScaleX(1);
                    view.setScaleY(1);
                    view.animate().setListener(null);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setScaleX(1);
                    view.setScaleY(1);
                    view.animate().setListener(null);
                    view.animate().scaleY(1.0f).scaleX(1.0f).setDuration(500).start();
                }
            });
            view.animate().start();
            mRequest = new LikeRequest(mPost.getId(), true);
            mPost.getLikeUserList().add(LoginManager.getCurrentUser());
        }
        mAdapter.notifyItemChanged(mViewHolder.getAdapterPosition(), Post.LIKE_PAY_LOAD);
        mRequest.enqueue();
    }

    @Override
    protected void onDestroy() {
        if (mRequest != null) {
            mRequest.cancel();
        }
        mImageView.animate().cancel();
    }
}
