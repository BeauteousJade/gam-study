package com.example.pby.gam_study.network.response.body;

import com.example.pby.gam_study.network.bean.MapBean;
import com.example.pby.gam_study.network.bean.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MineResponseBody {

    @SerializedName("user")
    private User mUser;
    @SerializedName("list")
    private List<MapBean> mDataList;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public List<MapBean> getDataList() {
        return mDataList;
    }

    public void setDataList(List<MapBean> dataList) {
        this.mDataList = dataList;
    }
}
