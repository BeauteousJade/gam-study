package com.example.pby.gam_study.page.card.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.browseImage.BrowseImageActivity;
import com.example.pby.gam_study.page.cardDetail.CardDetailActivity;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.pby.gam_study.RequestCode.REQUEST_UPDATE_CARD;

public class CardClickPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Card mCard;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter mAdapter;
    @Inject(AccessIds.ITEM_POSITION)
    int mPosition;

    @BindView(R.id.answer)
    TextView mAnswerView;

    private final BaseActivity.OnActivityResultListener mOnActivityResultListener = new BaseActivity.OnActivityResultListener() {

        @SuppressWarnings("unchecked")
        @Override
        public boolean onResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == REQUEST_UPDATE_CARD && resultCode == Activity.RESULT_OK && data != null) {
                Card updateCard = data.getParcelableExtra(CardDetailActivity.KEY_UPDATE_CARD);
                if (updateCard != null) {
                    mAdapter.setData(mPosition, updateCard, false);
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onBind() {
        if (mPosition == 0) {
            getCurrentActivity().addOnActivityResultListener(mOnActivityResultListener);
        }
        mAnswerView.setText(String.format(getString(R.string.answer_regex), mCard.getAnswer()));

        //
        mAnswerView.setAlpha(0);
    }

    @OnClick(R.id.image)
    public void onImageClick(View view) {
        CardDetailActivity.startActivity(getCurrentActivity(), mCard);
    }

    @OnClick(R.id.browse_edit_image)
    public void onEditClick(View view) {
        ArrayList<String> imageUrlList = new ArrayList<>();
        imageUrlList.add(mCard.getEditImageUrl());
        BrowseImageActivity.startActivity(getCurrentActivity(), imageUrlList);
    }

    @OnClick(R.id.browse_old_image)
    public void onOldClick(View view) {
        ArrayList<String> imageUrlList = new ArrayList<>();
        imageUrlList.add(mCard.getOldImageUrl());
        BrowseImageActivity.startActivity(getCurrentActivity(), imageUrlList);
    }

    @OnClick(R.id.see_answer)
    public void onClickSeeAnswer(View view) {
        mAnswerView.animate().cancel();
        if (mAnswerView.getAlpha() != 0) {
            mAnswerView.animate().alpha(0f).setDuration(200).start();
        } else {
            mAnswerView.animate().alpha(1.0f).setDuration(200).start();
        }
    }

    @Override
    protected void onUnBind() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
        mAnswerView.animate().cancel();
    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
        mAnswerView.animate().cancel();
    }
}
