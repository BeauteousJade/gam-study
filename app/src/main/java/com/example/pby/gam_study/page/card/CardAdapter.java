package com.example.pby.gam_study.page.card;

import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;

import java.util.List;

public class CardAdapter extends BaseRecyclerAdapter<Card> {
    public CardAdapter(List<Card> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return 0;
    }

    @Override
    protected Presenter onCreatePresenter() {
        return new Presenter();
    }
}
