package com.example.pby.gam_study.page.post.presenter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Comment;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;
import com.example.pby.gam_study.util.ArrayUtil;

import java.util.List;

import butterknife.BindView;

@Module(PostAdapter.Context.class)
public class PostCommentPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;

    @BindView(R.id.comment_container)
    LinearLayout mCommentContainer;


    @Override
    protected void onBind() {
        final List<Comment> commentList = mPost.getCommentList();
        mCommentContainer.removeAllViews();
        if (ArrayUtil.isEmpty(commentList)) {
            mCommentContainer.setVisibility(View.GONE);
        } else {
            mCommentContainer.setVisibility(View.VISIBLE);
            for (Comment comment : commentList) {
                mCommentContainer.addView(generateCommentTextView(comment));
            }
        }
    }

    private TextView generateCommentTextView(Comment comment) {
        TextView commentTextView = new TextView(getCurrentActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        commentTextView.setLayoutParams(lp);
        commentTextView.append(comment.getFromUser().getNickName());
        if (comment.getToUser() != null) {
            commentTextView.append(" " + getString(R.string.reply) + " ");
            return commentTextView;
        }
        commentTextView.append(": " + comment.getContent());
        return commentTextView;
    }
}
