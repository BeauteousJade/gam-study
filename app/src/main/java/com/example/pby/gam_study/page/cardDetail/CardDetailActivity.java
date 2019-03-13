package com.example.pby.gam_study.page.cardDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.util.ResourcesUtil;

import static com.example.pby.gam_study.RequestCode.REQUEST_UPDATE_CARD;

public class CardDetailActivity extends BaseActivity {

    public static final String KEY_CARD = "key_card";
    public static final String KEY_UPDATE_CARD = "update_card";

    private Card mCard;

    public static void startActivity(BaseActivity activity, Card card) {
        Intent intent = new Intent(activity, CardDetailActivity.class);
        intent.putExtra(KEY_CARD, card);
        activity.startActivityForResult(intent, REQUEST_UPDATE_CARD);
        activity.overridePendingTransition(R.anim.scale_in, 0);
    }

    @Override
    protected void onPrepare() {
        mCard = getIntent().getParcelableExtra(KEY_CARD);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(android.R.id.content).setBackgroundColor(ResourcesUtil.getColor(this, R.color.black));
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return CardDetailFragment.newInstance(mCard);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.scale_out);
    }
}
