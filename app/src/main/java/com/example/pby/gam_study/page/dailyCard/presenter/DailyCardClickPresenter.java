package com.example.pby.gam_study.page.dailyCard.presenter;

import android.view.View;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.browseImage.BrowseImageActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DailyCardClickPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Card mCard;

    @BindView(R.id.answer)
    TextView mAnswerView;

    @Override
    protected void onBind() {
        mAnswerView.setText(String.format(getString(R.string.answer_regex), mCard.getAnswer()));
        mAnswerView.setAlpha(0);
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
        mAnswerView.animate().cancel();
    }

    @Override
    protected void onDestroy() {
        mAnswerView.animate().cancel();
    }
}