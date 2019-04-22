package com.example.pby.gam_study.page.post.presenter;

import android.view.View;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.widget.LikeTextView;

import java.util.List;

import butterknife.BindView;

public class PostLikeShowPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;

    @BindView(R.id.like)
    LikeTextView mTextView;

    @Override
    protected void onBind() {
        final List<User> userList = mPost.getLikeUserList();
        if (ArrayUtil.isEmpty(userList)) {
            mTextView.setVisibility(View.GONE);
        } else {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("");
            appendLikeUser();
        }
    }

    private void appendLikeUser() {
        final List<User> userList = mPost.getLikeUserList();
        final int count = Math.min(5, userList.size());
        for (int i = 0; i < count; i++) {
            String userName = userList.get(i).getNickName();
            if (i != count - 1) {
                userName += "、";
            }
            mTextView.appendLikeUser(userName);
        }
        if (userList.size() > 5) {
            mTextView.append(String.format(getString(R.string.regex_like_count), userList.size()));
        }
    }
}
