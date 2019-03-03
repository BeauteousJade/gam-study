package com.example.pby.gam_study.page.home.page.home.presenter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.network.bean.Kind;
import com.example.pby.gam_study.page.card.CardActivity;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

@Module(BaseRecyclerAdapter.Context.class)
public class KindPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Kind mKind;
    @BindView(R.id.name)
    TextView mNameTextView;
    @BindView(R.id.count)
    TextView mCountTextView;
    @BindView(R.id.cover)
    ImageView mCoverImageView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBind() {
        Glide.with(getCurrentFragment()).asBitmap().load(mKind.getCover()).placeholder(R.mipmap.placeholder).into(mCoverImageView);
        mNameTextView.setText(mKind.getName());
        mCountTextView.setText(String.format(ResourcesUtil.getString(getCurrentActivity(), R.string.card_count_regex_text), mKind.getCount()));
    }

    @OnClick(R.id.cover)
    public void onCoverClick(View view) {
        CardActivity.startActivity(getCurrentActivity(), mKind.getId(), mKind.getName());
    }
}
