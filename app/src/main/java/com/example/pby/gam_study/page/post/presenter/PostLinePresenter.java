package com.example.pby.gam_study.page.post.presenter;

import android.view.View;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;
import com.example.pby.gam_study.util.ArrayUtil;

import butterknife.BindView;

@Module(PostAdapter.Context.class)
public class PostLinePresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;


    @BindView(R.id.line)
    View mLineView;

    @Override
    protected void onBind() {
        if (ArrayUtil.isEmpty(mPost.getCommentList()) && ArrayUtil.isEmpty(mPost.getLikeUserList())) {
            mLineView.setVisibility(View.GONE);
        } else {
            mLineView.setVisibility(View.VISIBLE);
        }
    }
}
