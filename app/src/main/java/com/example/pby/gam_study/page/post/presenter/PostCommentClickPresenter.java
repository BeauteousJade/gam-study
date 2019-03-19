package com.example.pby.gam_study.page.post.presenter;

import android.view.View;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.object.CommentObject;
import com.example.pby.gam_study.page.post.NewsPageFragment;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnClick;

@Module(PostAdapter.Context.class)
public class PostCommentClickPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;
    @Inject(AccessIds.VIEW_HOLDER)
    RecyclerView.ViewHolder mViewHolder;
    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;


    @OnClick(R.id.comment_operation)
    public void onCommentClick(View view) {
        mObservable.notifyChanged(NewsPageFragment.KEY_EXPRESSION_CLICK, new CommentObject(mPost, mViewHolder.getAdapterPosition(), mPost.getId()));
    }
}
