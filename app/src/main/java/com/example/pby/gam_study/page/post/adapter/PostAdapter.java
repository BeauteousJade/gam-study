package com.example.pby.gam_study.page.post.adapter;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.page.post.presenter.PostCommentOperationPresenter;
import com.example.pby.gam_study.page.post.presenter.PostCommentPresenter;
import com.example.pby.gam_study.page.post.presenter.PostContentPresenter;
import com.example.pby.gam_study.page.post.presenter.PostImagePresenter;
import com.example.pby.gam_study.page.post.presenter.PostLikePresenter;
import com.example.pby.gam_study.page.post.presenter.PostLikeShowPresenter;
import com.example.pby.gam_study.page.post.presenter.PostLinePresenter;
import com.example.pby.gam_study.page.post.presenter.PostUserPresenter;

import java.util.List;

public class PostAdapter extends BaseRecyclerAdapter<Post> {
    public PostAdapter(List<Post> dataList) {
        super(dataList);
        putExtra(AccessIds.CLICKABLE, true);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_post;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new PostUserPresenter());
        presenter.add(new PostContentPresenter());
        presenter.add(new PostImagePresenter());
        presenter.add(new PostLikeShowPresenter());
        presenter.add(new PostCommentPresenter());
        presenter.add(new PostLikePresenter());
        presenter.add(new PostCommentOperationPresenter());
        presenter.add(new PostLinePresenter());
        return presenter;
    }
}
