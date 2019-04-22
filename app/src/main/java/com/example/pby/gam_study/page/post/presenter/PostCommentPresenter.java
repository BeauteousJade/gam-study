package com.example.pby.gam_study.page.post.presenter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Comment;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.object.CommentObject;
import com.example.pby.gam_study.other.MyLinkedMovementMethod;
import com.example.pby.gam_study.page.post.PostFragment;
import com.example.pby.gam_study.page.profile.UserProfileActivity;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.widget.EmojiTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class PostCommentPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;
    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    @Inject(AccessIds.VIEW_HOLDER)
    RecyclerView.ViewHolder mViewHolder;
    @Inject(AccessIds.PAYLOAD)
    List<String> mPayload;
    @Inject(AccessIds.CLICKABLE)
    boolean isClickable;

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
            if (ArrayUtil.isEmpty(mPayload)) {
                mCommentContainer.removeAllViews();
                for (Comment comment : commentList) {
                    mCommentContainer.addView(generateCommentTextView(comment));
                }
            } else if (mPayload.get(0).contains(Post.COMMENT_PAY_LOAD)) {
                mCommentContainer.addView(generateCommentTextView(commentList.get(commentList.size() - 1)));
            }
        }
    }

    private TextView generateCommentTextView(Comment comment) {
        EmojiTextView commentTextView = new EmojiTextView(getCurrentActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        commentTextView.setLayoutParams(lp);
        commentTextView.setTextColor(getColor(R.color.black));
        commentTextView.append(generateNickName(comment.getFromUser()));
        if (comment.getToUser() != null) {
            commentTextView.append(" " + getString(R.string.reply) + " ");
            commentTextView.append(generateNickName(comment.getToUser()));
        }
        commentTextView.appendContent(": " + comment.getContent());
        commentTextView.setBackground(getDrawable(R.drawable.bg_item));
        commentTextView.setMovementMethod(MyLinkedMovementMethod.getInstance());
        commentTextView.setOnClickListener(v -> mObservable.notifyChanged(PostFragment.KEY_ADD_COMMENT, new CommentObject(comment, mViewHolder.getAdapterPosition(), mPost.getId())));
        return commentTextView;
    }

    private SpannableString generateNickName(User user) {
        final SpannableString spannableString = new SpannableString(user.getNickName());
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getColor(R.color.color_main_blue));
        if (isClickable) {
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    UserProfileActivity.startActivity(getCurrentActivity(), user);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setUnderlineText(false);
                }
            };
            spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannableString.setSpan(foregroundColorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
