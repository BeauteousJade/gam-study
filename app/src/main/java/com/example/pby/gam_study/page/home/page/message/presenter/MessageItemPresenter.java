package com.example.pby.gam_study.page.home.page.message.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.GlideFactory;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.MessageItem;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.util.TimeUtil;
import com.example.pby.gam_study.widget.EmojiTextView;

import java.util.Objects;

import butterknife.BindView;

public class MessageItemPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    MessageItem mMessageItem;
    @Inject(AccessIds.PAYLOAD)
    String mPayload;

    @BindView(R.id.avatar)
    ImageView mAvatarView;
    @BindView(R.id.content)
    EmojiTextView mContentView;
    @BindView(R.id.nick_name)
    TextView mNameView;
    @BindView(R.id.time)
    TextView mTimeView;
    @BindView(R.id.unread_count)
    TextView mUnReadCountView;

    @Override
    protected void onBind() {
        boolean isFromUser = Objects.equals(LoginManager.getCurrentUser().getId(), mMessageItem.getFromUser().getId());
        final User otherUser = isFromUser ? mMessageItem.getToUser() : mMessageItem.getFromUser();
        GlideApp.with(getCurrentFragment()).asBitmap().apply(GlideFactory.createCircleOption()).load(otherUser.getHead()).into(mAvatarView);
        if (mPayload == null || mPayload.contains(MessageItem.CONTENT)) {
            mContentView.setContent(mMessageItem.getRecentContent());
        }
        if (mPayload == null || mPayload.contains(MessageItem.TIME)) {
            mTimeView.setText(TimeUtil.formatTime(mMessageItem.getRecentTime()));
        }
        if (isFromUser) {
            if (mMessageItem.getFromUserUnReadCount() != 0) {
                mUnReadCountView.setVisibility(View.VISIBLE);
                mUnReadCountView.setText(String.valueOf(mMessageItem.getFromUserUnReadCount()));
            } else {
                mUnReadCountView.setVisibility(View.GONE);
            }
        } else {
            if (mMessageItem.getToUserUnReadCount() != 0) {
                mUnReadCountView.setVisibility(View.VISIBLE);
                mUnReadCountView.setText(String.valueOf(mMessageItem.getToUserUnReadCount()));
            } else {
                mUnReadCountView.setVisibility(View.GONE);
            }
        }
        mNameView.setText(otherUser.getNickName());
    }
}
