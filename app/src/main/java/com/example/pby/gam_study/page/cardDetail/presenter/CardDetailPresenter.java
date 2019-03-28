package com.example.pby.gam_study.page.cardDetail.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.RequestCode;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.cardDetail.CardDetailActivity;
import com.example.pby.gam_study.page.editCard.EditCardActivity;
import com.example.pby.gam_study.util.DisplayUtil;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class CardDetailPresenter extends Presenter implements View.OnClickListener {

    @BindView(R.id.title_bar)
    View mTitleBarView;
    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.right_icon)
    ImageView mRightView;

    @Inject(AccessIds.CARD)
    Card mCard;


    private Card mUpdateCard;
    private GamDialogFragment mDialogFragment;


    private final BaseActivity.OnActivityResultListener mOnActivityResultListener = new BaseActivity.OnActivityResultListener() {
        @Override
        public boolean onResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == RequestCode.REQUEST_EDIT_CARD && resultCode == Activity.RESULT_OK && data != null) {
                mUpdateCard = data.getParcelableExtra(EditCardActivity.KEY_EDIT_CARD);
                if (mUpdateCard != null) {
                    mCard.copy(mUpdateCard);
                    getCurrentFragment().refresh();
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate() {
        mDialogFragment = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER_BOTTOM, R.layout.menu_card_detail)
                .setAnchorView(getCurrentActivity().findViewById(android.R.id.content))
                .setWindowWidth(DisplayUtil.getScreenWidth(getCurrentActivity()))
                .setAnimationStyle(R.style.menu_show_animation)
                .setOnViewClickListener(this, R.id.edit_old, R.id.edit_new, R.id.cancel)
                .build();
    }

    @Override
    protected void onBind() {
        mTitleBarView.setBackground(null);
        mLeftView.setImageResource(R.drawable.bg_back);
        mRightView.setImageResource(R.drawable.bg_more);
        getCurrentActivity().addOnActivityResultListener(mOnActivityResultListener);
    }

    @Override
    protected void onUnBind() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        if (mUpdateCard != null) {
            Intent data = new Intent();
            data.putExtra(CardDetailActivity.KEY_UPDATE_CARD, mUpdateCard);
            getCurrentActivity().setResult(Activity.RESULT_OK, data);
        }
        getCurrentActivity().finish();
    }

    @OnClick(R.id.right_icon)
    public void onRightIcon(View view) {
        mDialogFragment.show(getCurrentFragment().getChildFragmentManager(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_old:
                EditCardActivity.startActivity(getCurrentActivity(), mCard, EditCardActivity.TYPE_EDI_OLD_CARD);
                break;
            case R.id.edit_new:
                EditCardActivity.startActivity(getCurrentActivity(), mCard, EditCardActivity.TYPE_EDIT_NEW_CARD);
                break;
        }
        mDialogFragment.dismiss();
    }
}
