package com.example.pby.gam_study.page.home.page.message.presenter;

import android.view.View;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.MessageItem;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.chat.ChatActivity;
import com.example.pby.gam_study.page.home.page.message.MessageAdapter;
import com.example.pby.gam_study.page.home.page.message.request.DeleteMessageItemRequest;
import com.example.pby.gam_study.page.home.page.message.request.ResetUnReadCountRequest;

import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnClick;

public class MessageItemClickPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    MessageItem mMessageItem;
    @Inject(AccessIds.VIEW_HOLDER)
    RecyclerView.ViewHolder mViewHolder;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    MessageAdapter mAdapter;

    @OnClick(R.id.message_item_container)
    public void onMessageItemClick(View view) {
        boolean isFromUser = Objects.equals(LoginManager.getCurrentUser().getId(), mMessageItem.getFromUser().getId());
        if (isFromUser) {
            mMessageItem.setFromUserUnReadCount(0);
        } else {
            mMessageItem.setToUserUnReadCount(0);
        }
        mAdapter.notifyItemChanged(mViewHolder.getAdapterPosition(), MessageItem.COUNT);
        new ResetUnReadCountRequest(mMessageItem.getId(), isFromUser).enqueue();

        User otherUser = isFromUser ? mMessageItem.getToUser() : mMessageItem.getFromUser();
        ChatActivity.startActivity(getCurrentActivity(), otherUser);
    }

    @OnClick(R.id.delete)
    public void onDeleteClick(View view) {
        mAdapter.remove(mViewHolder.getAdapterPosition());
        new DeleteMessageItemRequest(Objects.equals(LoginManager.getCurrentUser().getId()
                , mMessageItem.getFromUser().getId()), mMessageItem.getId()).enqueue();
    }
}
