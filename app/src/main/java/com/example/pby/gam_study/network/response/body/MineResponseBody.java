package com.example.pby.gam_study.network.response.body;

import com.example.pby.gam_study.network.bean.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MineResponseBody {

    @SerializedName("user")
    private User mUser;
    @SerializedName("list")
    private List<Integer> mDataList;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public List<Integer> getDataList() {
        return mDataList;
    }

    public void setDataList(List<Integer> dataList) {
        this.mDataList = dataList;
    }
}
