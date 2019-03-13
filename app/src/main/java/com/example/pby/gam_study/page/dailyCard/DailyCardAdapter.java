package com.example.pby.gam_study.page.dailyCard;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.dailyCard.presenter.DailyCardClickPresenter;
import com.example.pby.gam_study.page.dailyCard.presenter.DailyCardPresenter;

import java.util.List;

public class DailyCardAdapter extends BaseRecyclerAdapter<Card> {

    public DailyCardAdapter(List<Card> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_card;
    }

    @Override
    protected Presenter onCreatePresenter(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new DailyCardPresenter());
        presenter.add(new DailyCardClickPresenter());
        return presenter;
    }
}
