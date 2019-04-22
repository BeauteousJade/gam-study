package com.example.pby.gam_study.page.setting.presenter;

import android.widget.TextView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Setting;
import com.example.pby.gam_study.page.setting.SettingFragment;
import com.example.pby.gam_study.util.SharedPreferencesUtil;
import com.example.pby.gam_study.widget.ToggleButton;

import butterknife.BindView;

public class SettingItemPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Setting mSetting;

    @BindView(R.id.name)
    TextView mNameView;
    @BindView(R.id.toggleButton)
    ToggleButton mToggleButton;

    private final ToggleButton.OnCheckedChangeListener mOnCheckedChangeListener = new ToggleButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChange(boolean checked) {
            switch (mSetting.getSettingName()) {
                case "开启通知":
                    SharedPreferencesUtil.putBoolean(getCurrentActivity(), SettingFragment.NOTIFICATION, checked);
                    break;
                case "开启铃声":
                    SharedPreferencesUtil.putBoolean(getCurrentActivity(), SettingFragment.RINGTONE, checked);
                    break;
            }
        }
    };

    @Override
    protected void onBind() {
        mNameView.setText(mSetting.getSettingName());
        mToggleButton.setChecked(mSetting.isChecked());
        mToggleButton.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }
}
