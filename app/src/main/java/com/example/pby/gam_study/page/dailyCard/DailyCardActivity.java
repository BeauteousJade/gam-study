package com.example.pby.gam_study.page.dailyCard;

import android.content.Intent;

import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.network.bean.Card;

import java.util.ArrayList;
import java.util.List;

public class DailyCardActivity extends BaseActivity {

    public static final String KEY_CARD_LIST = "key_card_list";

    private List<Card> mCardList;

    public static void startActivity(BaseActivity activity, ArrayList<Card> cardList) {
        Intent intent = new Intent(activity, DailyCardActivity.class);
        intent.putParcelableArrayListExtra(KEY_CARD_LIST, cardList);
        activity.startActivity(intent);
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mCardList = getIntent().getParcelableArrayListExtra(KEY_CARD_LIST);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return DailyCardFragment.newInstance((ArrayList<Card>) mCardList);
    }
}
