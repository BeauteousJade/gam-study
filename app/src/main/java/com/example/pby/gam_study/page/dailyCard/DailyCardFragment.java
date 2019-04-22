package com.example.pby.gam_study.page.dailyCard;

import android.os.Bundle;

import com.blade.annotation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.dailyCard.presenter.DailyCardSlidePresenter;
import com.example.pby.gam_study.page.dailyCard.presenter.DailyCardTitleBarPresenter;
import com.example.pby.gam_study.widget.layoutManager.SlideItemTouchCallback;
import com.example.pby.gam_study.widget.layoutManager.SlideLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DailyCardFragment extends RecyclerViewFragment {

    private static final int MAX_VISIBLE_COUNT = 3;
    private List<Card> mCardList;

    public static DailyCardFragment newInstance(ArrayList<Card> cardList) {
        DailyCardFragment dailyCardFragment = new DailyCardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DailyCardActivity.KEY_CARD_LIST, cardList);
        dailyCardFragment.setArguments(bundle);
        return dailyCardFragment;
    }

    @Override
    protected void onPrepare() {
        mCardList = Objects.requireNonNull(getArguments()).getParcelableArrayList(DailyCardActivity.KEY_CARD_LIST);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily_card;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new DailyCardTitleBarPresenter());
        presenter.add(new DailyCardSlidePresenter());
        return presenter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mContext = super.onCreateBaseContext();
        context.mCardList = mCardList;
        return (T) context;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new DailyCardAdapter(mCardList);
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new SlideLayoutManager(MAX_VISIBLE_COUNT, SlideLayoutManager.Gravity.bottom);
    }

    @Override
    protected ItemTouchHelper onCreateItemTouchHelper() {
        return new ItemTouchHelper(getCallback());
    }

    @Override
    public ItemTouchHelper.Callback onCreateCallback() {
        return new SlideItemTouchCallback(getRecyclerAdapter(), MAX_VISIBLE_COUNT);
    }

    public static class Context {
        @Provides(deepProvides = true)
        public RecyclerViewFragment.Context mContext;
        @Provides(AccessIds.LIST)
        public List<Card> mCardList;
    }
}
