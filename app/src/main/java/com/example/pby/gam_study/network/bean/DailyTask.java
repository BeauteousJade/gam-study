package com.example.pby.gam_study.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyTask {
    @SerializedName("sign")
    private boolean mIsSign;
    @SerializedName("dailyTask")
    private List<Card> mDailyCard;
    @SerializedName("dailyTaskCount")
    private int mDailyTaskCount;


    public int getDailyTaskCount() {
        return mDailyTaskCount;
    }

    public void setDailyTaskCount(int dailyTaskCount) {
        mDailyTaskCount = dailyTaskCount;
    }

    public boolean isSign() {
        return mIsSign;
    }

    public void setIsSign(boolean isSign) {
        mIsSign = isSign;
    }

    public List<Card> getDailyCard() {
        return mDailyCard;
    }

    public void setDailyCard(List<Card> dailyCard) {
        mDailyCard = dailyCard;
    }

}
