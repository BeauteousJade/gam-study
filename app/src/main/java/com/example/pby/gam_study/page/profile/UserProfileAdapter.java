package com.example.pby.gam_study.page.profile;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.post.presenter.PostCommentOperationPresenter;
import com.example.pby.gam_study.page.post.presenter.PostCommentPresenter;
import com.example.pby.gam_study.page.post.presenter.PostContentPresenter;
import com.example.pby.gam_study.page.post.presenter.PostImagePresenter;
import com.example.pby.gam_study.page.post.presenter.PostLikePresenter;
import com.example.pby.gam_study.page.post.presenter.PostLikeShowPresenter;
import com.example.pby.gam_study.page.post.presenter.PostLinePresenter;
import com.example.pby.gam_study.page.post.presenter.PostUserPresenter;
import com.example.pby.gam_study.page.profile.presenter.ChatPresenter;
import com.example.pby.gam_study.page.profile.presenter.FollowPresenter;
import com.example.pby.gam_study.page.profile.presenter.UserHeadPresenter;

import java.util.List;

public class UserProfileAdapter extends BaseRecyclerAdapter<Object> {

    private static final int TYPE_HEAD = 1;
    private static final int TYPE_ITEM = 2;

    public UserProfileAdapter(List<Object> dataList, User user) {
        super(dataList);
        putExtra(AccessIds.CLICKABLE, false);
        putExtra(AccessIds.USER, user);
    }

    @Override
    public int getItemViewTypeNoEmpty(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                return R.layout.item_profile_head;
            case TYPE_ITEM:
                return R.layout.item_post;
        }
        return 0;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        switch (viewType) {
            case TYPE_HEAD:
                presenter.add(new UserHeadPresenter());
                presenter.add(new FollowPresenter());
                presenter.add(new ChatPresenter());
                break;
            case TYPE_ITEM:
                presenter.add(new PostUserPresenter());
                presenter.add(new PostContentPresenter());
                presenter.add(new PostImagePresenter());
                presenter.add(new PostLikeShowPresenter());
                presenter.add(new PostCommentPresenter());
                presenter.add(new PostLikePresenter());
                presenter.add(new PostCommentOperationPresenter());
                presenter.add(new PostLinePresenter());
                break;
        }
        return presenter;
    }
}
