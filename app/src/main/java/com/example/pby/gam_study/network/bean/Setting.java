package com.example.pby.gam_study.network.bean;

public class Setting {

    private String mSettingName;
    private boolean isChecked;

    public Setting(String settingName, boolean isChecked) {
        this.mSettingName = settingName;
        this.isChecked = isChecked;
    }

    public String getSettingName() {
        return mSettingName;
    }

    public void setSettingName(String settingName) {
        this.mSettingName = settingName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
