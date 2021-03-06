package com.example.pby.gam_study.page.card;

import android.os.Bundle;
import android.view.View;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.card.presenter.CardTitleBarPresenter;
import com.example.pby.gam_study.page.card.presenter.CardUpdateTimePresenter;
import com.example.pby.gam_study.page.card.presenter.SlideCardPresenter;
import com.example.pby.gam_study.page.card.request.CardRequest;
import com.example.pby.gam_study.widget.layoutManager.SlideItemTouchCallback;
import com.example.pby.gam_study.widget.layoutManager.SlideLayoutManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CardFragment extends RefreshRecyclerViewFragment {

    private static final String KIND_ID = "kind_id";
    private static final String TITLE = "title";
    private static final int MAX_VISIBLE_COUNT = 3;
    private String mKindId;
    private String mTitle;

    public static CardFragment newInstance(String kindId, String kindName) {
        final CardFragment cardFragment = new CardFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(KIND_ID, kindId);
        bundle.putString(TITLE, kindName);
        cardFragment.setArguments(bundle);
        return cardFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mKindId = getArguments().getString(KIND_ID);
            mTitle = getArguments().getString(TITLE);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        putExtra(AccessIds.TITLE, mTitle);
        putExtra(AccessIds.KIND_ID, mKindId);
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new SlideCardPresenter());
        presenter.add(new CardTitleBarPresenter());
        presenter.add(new CardUpdateTimePresenter());
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card;
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
    public ItemTouchHelper.Callback onCreateCallback() {
        return new SlideItemTouchCallback(getRecyclerAdapter(), MAX_VISIBLE_COUNT);
    }

    @Override
    protected ItemTouchHelper onCreateItemTouchHelper() {
        return new ItemTouchHelper(getCallback());
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new SlideLayoutManager(MAX_VISIBLE_COUNT, SlideLayoutManager.Gravity.bottom);
    }
}
