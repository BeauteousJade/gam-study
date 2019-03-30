package com.example.pby.gam_study.page.chat;

import android.os.Bundle;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends RecyclerViewFragment {

    private String mToUserId;

    public static ChatFragment newInstance(String toUserId) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ChatActivity.TO_USER_ID, toUserId);
        chatFragment.setArguments(bundle);
        return chatFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mToUserId = Objects.requireNonNull(getArguments()).getString(ChatActivity.TO_USER_ID);
        putExtra(AccessIds.USER_ID, mToUserId);
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new ChatAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }
}
