package com.example.pby.gam_study.page.cardDetail;

import android.os.Bundle;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.cardDetail.presenter.CardDetailPresenter;
import com.example.pby.gam_study.page.cardDetail.presenter.ImageLoadPresenter;

import java.util.Objects;

public class CardDetailFragment extends BaseFragment {

    private Card mCard;

    public static CardDetailFragment newInstance(Card card) {
        CardDetailFragment cardDetailFragment = new CardDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CardDetailActivity.KEY_CARD, card);
        cardDetailFragment.setArguments(bundle);
        return cardDetailFragment;
    }

    @Override
    public void onPrepareBaseContext() {
        mCard = Objects.requireNonNull(getArguments()).getParcelable(CardDetailActivity.KEY_CARD);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mContext = super.onCreateBaseContext();
        context.mCard = mCard;
        return (T) context;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new CardDetailPresenter());
        presenter.add(new ImageLoadPresenter());
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card_detail;
    }

    public static class Context {
        @Provides(deepProvides = true)
        public BaseFragment.Context mContext;
        @Provides(AccessIds.CARD)
        public Card mCard;
    }
}
