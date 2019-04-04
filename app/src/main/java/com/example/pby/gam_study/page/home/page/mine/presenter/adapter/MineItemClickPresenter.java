package com.example.pby.gam_study.page.home.page.mine.presenter.adapter;

import android.view.View;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.MapBean;
import com.example.pby.gam_study.page.kind.KindActivity;

import butterknife.OnClick;

public class MineItemClickPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    MapBean mMapBean;

    @OnClick(R.id.item_container)
    public void onItemContainerClick(View view) {
        switch (mMapBean.getKey()) {
            case "分类":
                KindActivity.startActivity(getCurrentActivity(), LoginManager.getCurrentUser().getId());
                break;
            case "卡片":
                break;
        }
    }

}
