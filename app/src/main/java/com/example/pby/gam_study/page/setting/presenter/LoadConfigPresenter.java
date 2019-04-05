package com.example.pby.gam_study.page.setting.presenter;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Setting;
import com.example.pby.gam_study.page.setting.SettingAdapter;
import com.example.pby.gam_study.page.setting.SettingFragment;
import com.example.pby.gam_study.util.SharedPreferencesUtil;

import java.util.List;

public class LoadConfigPresenter extends Presenter {

    @Inject(AccessIds.RECYCLER_ADAPTER)
    SettingAdapter mAdapter;

    @Override
    protected void onBind() {
        List<Object> dataList = mAdapter.getDataList();
        ((Setting) dataList.get(0)).setChecked(SharedPreferencesUtil.getBoolean(getCurrentActivity(), SettingFragment.NOTIFICATION, false));
        ((Setting) dataList.get(1)).setChecked(SharedPreferencesUtil.getBoolean(getCurrentActivity(), SettingFragment.RINGTONE, false));
        mAdapter.notifyItemRangeChanged(0, 2, "");
    }
}
