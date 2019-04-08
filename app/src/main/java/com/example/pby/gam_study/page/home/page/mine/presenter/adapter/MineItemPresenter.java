package com.example.pby.gam_study.page.home.page.mine.presenter.adapter;

import android.widget.TextView;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.MapBean;

import java.util.List;

import butterknife.BindView;

public class MineItemPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    MapBean mMapBean;
    @Inject(AccessIds.PAYLOAD)
    List<String> mPayloads;

    @BindView(R.id.item_title)
    TextView mTitleView;
    @BindView(R.id.spare)
    TextView mSpareView;

    @Override
    protected void onBind() {
        mTitleView.setText(mMapBean.getKey());
        if (Integer.valueOf(mMapBean.getValue()) >= 0) {
            mSpareView.setText(mMapBean.getValue());
        } else {
            mSpareView.setText("");
        }
    }
}
