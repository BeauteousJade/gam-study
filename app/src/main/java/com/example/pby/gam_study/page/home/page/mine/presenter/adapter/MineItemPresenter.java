package com.example.pby.gam_study.page.home.page.mine.presenter.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.MapBean;
import com.example.pby.gam_study.util.ArrayUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
        String payload = ArrayUtil.isEmpty(mPayloads) ? null:mPayloads.get(0);
        if(payload == null || payload.contains(MapBean.KEY)) {
            mTitleView.setText(mMapBean.getKey());
        }
        if(payload == null || payload.contains(MapBean.VALUE)) {
            if (Integer.valueOf(mMapBean.getValue()) >= 0) {
                mSpareView.setText(mMapBean.getValue());
            } else {
                mSpareView.setText("");
            }
        }
    }

    @OnClick(R.id.item_container)
    public void onItemClick(View view) {

    }
}
