package com.example.pby.gam_study.network.bean;

import com.google.gson.annotations.SerializedName;

public class Kind {
    @SerializedName("id")
    private String mId;
    @SerializedName("cover")
    private String mCover;
    @SerializedName("count")
    private int mCount;
    @SerializedName("recentBrowserTime")
    private long mRecentBrowserTime;
    @SerializedName("name")
    private String mName;
    @SerializedName("userId")
    private String mUserId;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        this.mCover = cover;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    public long getRecentBrowserTime() {
        return mRecentBrowserTime;
    }

    public void setRecentBrowserTime(long recentBrowserTime) {
        this.mRecentBrowserTime = recentBrowserTime;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserId() {
        return mUserId;
    }
}
