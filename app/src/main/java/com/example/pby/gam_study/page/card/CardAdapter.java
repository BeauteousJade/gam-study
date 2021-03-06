package com.example.pby.gam_study.page.card;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.card.presenter.CardClickPresenter;
import com.example.pby.gam_study.page.card.presenter.CardPresenter;

import java.util.List;

public class CardAdapter extends BaseRecyclerAdapter<Card> {

    public CardAdapter(List<Card> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_card;
    }

    @Override
    protected Presenter onCreatePresenterIfNoEmpty(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new CardPresenter());
        presenter.add(new CardClickPresenter());
        return presenter;
    }

    @Override
    public boolean onItemRemove(int position) {
        mDataList.remove(position);
        if (mDataList.isEmpty()) {
            mDataList.add(null);
        }
        notifyDataSetChanged();
        return true;
    }
}
