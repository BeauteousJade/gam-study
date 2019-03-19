package com.example.pby.gam_study.page.post.presenter;

import android.view.View;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;
import com.example.pby.gam_study.util.StringUtil;
import com.example.pby.gam_study.widget.EmojiTextView;

import butterknife.BindView;

@Module(PostAdapter.Context.class)
public class PostContentPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;
    @Inject(AccessIds.PAYLOAD)
    Object mPayload;

    @BindView(R.id.post_content)
    EmojiTextView mTextView;


    @Override
    protected void onBind() {
        if (StringUtil.isEmpty(mPost.getContent())) {
            mTextView.setVisibility(View.GONE);
        } else {
            if (mPayload == null) {
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setContent(mPost.getContent());
            }
        }
    }
}
