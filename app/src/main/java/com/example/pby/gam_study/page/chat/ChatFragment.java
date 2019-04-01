package com.example.pby.gam_study.page.chat;

import android.os.Bundle;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.LinearLayoutManagerVerticalItemDecoration;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.chat.presenter.chat.ChatInputPresenter;
import com.example.pby.gam_study.page.chat.presenter.chat.ChatRefreshPresenter;
import com.example.pby.gam_study.page.chat.presenter.chat.ChatTitleBarPresenter;
import com.example.pby.gam_study.page.chat.presenter.chat.NewMessagePresenter;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends RecyclerViewFragment {

    public static ChatFragment newInstance(User toUser) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ChatActivity.TO_USER, toUser);
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
        putExtra(AccessIds.USER, Objects.requireNonNull(getArguments()).getParcelable(ChatActivity.TO_USER));
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new ChatRefreshPresenter());
        presenter.add(new ChatTitleBarPresenter());
        presenter.add(new ChatInputPresenter());
        presenter.add(new NewMessagePresenter());
        return presenter;
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new LinearLayoutManagerVerticalItemDecoration(ResourcesUtil.getColor(requireContext()
                , R.color.transparent), DisplayUtil.dpToPx(requireContext(), 10)));
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
