package com.example.pby.gam_study.page.post.presenter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Comment;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.object.CommentObject;
import com.example.pby.gam_study.page.post.NewsPageFragment;
import com.example.pby.gam_study.page.post.adapter.PostAdapter;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.widget.EmojiTextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

@Module(PostAdapter.Context.class)
public class PostCommentPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;
    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    @Inject(AccessIds.VIEW_HOLDER)
    RecyclerView.ViewHolder mViewHolder;
    @Inject(AccessIds.PAYLOAD)
    Object mPayload;

    @BindView(R.id.comment_container)
    LinearLayout mCommentContainer;

    @Override
    protected void onBind() {
        final List<Comment> commentList = mPost.getCommentList();
        if (ArrayUtil.isEmpty(commentList)) {
            mCommentContainer.removeAllViews();
            mCommentContainer.setVisibility(View.GONE);
        } else {
            mCommentContainer.setVisibility(View.VISIBLE);
            if (mPayload == null) {
                mCommentContainer.removeAllViews();
                for (Comment comment : commentList) {
                    mCommentContainer.addView(generateCommentTextView(comment));
                }
            } else {
                mCommentContainer.addView(generateCommentTextView(commentList.get(commentList.size() - 1)));
            }
        }
    }

    private TextView generateCommentTextView(Comment comment) {
        EmojiTextView commentTextView = new EmojiTextView(getCurrentActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        commentTextView.setLayoutParams(lp);
        commentTextView.append(comment.getFromUser().getNickName());
        if (comment.getToUser() != null) {
            commentTextView.append(" " + getString(R.string.reply) + " ");
            commentTextView.append(comment.getToUser().getNickName());
        }
        commentTextView.appendContent(": " + comment.getContent());
        commentTextView.setBackground(getDrawable(R.drawable.bg_item));
        commentTextView.setOnClickListener(v -> mObservable.notifyChanged(NewsPageFragment.KEY_ADD_COMMENT, new CommentObject(comment, mViewHolder.getAdapterPosition(), mPost.getId())));
        return commentTextView;
    }
}
