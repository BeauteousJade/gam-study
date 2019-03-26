package com.example.pby.gam_study.page.editCard;

import android.content.Intent;

import com.example.pby.gam_study.RequestCode;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.network.bean.Card;

public class EditCardActivity extends BaseActivity {

    public static final int TYPE_NEW_CARD = 0;
    public static final int TYPE_EDI_OLD_CARD = 1;
    public static final int TYPE_EDIT_NEW_CARD = 2;

    public static final String KIND_ID = "kind_id";
    public static final String CARD = "card";
    public static final String KEY_TYPE = "type";

    public static final String KEY_EDIT_CARD = "edit_card";

    private String mKindId;
    private Card mCard;
    private int mType = TYPE_NEW_CARD;

    public static void startActivity(BaseActivity activity, String kindId) {
        Intent intent = new Intent(activity, EditCardActivity.class);
        intent.putExtra(KIND_ID, kindId);
        intent.putExtra(KEY_TYPE, TYPE_NEW_CARD);
        activity.startActivity(intent);
    }

    public static void startActivity(BaseActivity activity, Card card, int type) {
        Intent intent = new Intent(activity, EditCardActivity.class);
        intent.putExtra(CARD, card);
        intent.putExtra(KEY_TYPE, type);
        activity.startActivityForResult(intent, RequestCode.REQUEST_EDIT_CARD);
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mType = getIntent().getIntExtra(KEY_TYPE, TYPE_NEW_CARD);
        switch (mType) {
            case TYPE_NEW_CARD:
                mKindId = getIntent().getStringExtra(KIND_ID);
                break;
            case TYPE_EDIT_NEW_CARD:
            case TYPE_EDI_OLD_CARD:
                mCard = getIntent().getParcelableExtra(CARD);
                break;
        }
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        final EditCardFragment newCardFragment;
        switch (mType) {
            case EditCardActivity.TYPE_EDIT_NEW_CARD:
            case EditCardActivity.TYPE_EDI_OLD_CARD:
                newCardFragment = EditCardFragment.newInstance(mCard, mType);
                break;
            default:
                newCardFragment = EditCardFragment.newInstance(mKindId);
                break;
        }
        return newCardFragment;
    }
}
