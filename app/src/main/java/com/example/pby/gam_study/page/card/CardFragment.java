package com.example.pby.gam_study.page.card;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.card.presenter.CardTitleBarPresenter;

import java.util.ArrayList;

public class CardFragment extends RefreshRecyclerViewFragment {

    private static final String KIND_ID = "kind_id";
    private static final String KIND_NAME = "kind_name";
    private String mKindId;
    private String mKindName;

    public static CardFragment newInstance(String kindId, String kindName) {
        final CardFragment cardFragment = new CardFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(KIND_ID, kindId);
        bundle.putString(KIND_NAME, kindName);
        cardFragment.setArguments(bundle);
        return cardFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mKindId = getArguments().getString(KIND_ID);
            mKindName = getArguments().getString(KIND_NAME);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public Object onCreateBaseContext() {
        Context context = new Context();
        context.mContext = (RefreshRecyclerViewFragment.Context) super.onCreateBaseContext();
        context.mKindName = mKindName;
        context.mKindId = mKindId;
        return context;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new CardTitleBarPresenter());
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_title;
    }

    @Override
    public Request onCreateRequest() {
        return new CardRequest(mKindId);
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new CardAdapter(new ArrayList<>());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createGridLayoutManagerIfEmpty(requireContext(), getRecyclerAdapter(), 3, LayoutManagerFactory.DEFAULT_SPAN_FULL);
    }

    public static class Context {
        @Provides(value = AccessIds.TITLE)
        public String mKindName;
        @Provides(value = AccessIds.KIND_ID)
        public String mKindId;
        @Provides(deepProvides = true)
        public RefreshRecyclerViewFragment.Context mContext;
    }
}
