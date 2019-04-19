package com.example.pby.gam_study.page.sendPost.presenter;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.util.StringUtil;
import com.example.pby.gam_study.widget.EmojiEditText;

import butterknife.BindView;

public class PostContentPresenter extends Presenter {

    @Inject(AccessIds.POST_CONTENT)
    String mPostContent;

    @BindView(R.id.post_content)
    EmojiEditText mPostContentView;


    @Override
    protected void onBind() {
        if (!StringUtil.isEmpty(mPostContent)) {
            mPostContentView.setContent(mPostContent);
        }
    }
}
