package com.example.pby.gam_study.network.bean;

import com.google.gson.annotations.SerializedName;

public class MessageItem {

    @SerializedName("id")
    private String mId;
    @SerializedName("fromUser")
    private User mFromUser;
    @SerializedName("toUser")
    private User mToUser;
    @SerializedName("recentContent")
    private String mRecentContent;
    @SerializedName("recentTime")
    private long mRecentTime;
    @SerializedName("time")
    private long mTime;


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public User getFromUser() {
        return mFromUser;
    }

    public void setFromUser(User fromUser) {
        this.mFromUser = fromUser;
    }

    public User getmoUser() {
        return mToUser;
    }

    public void setToUser(User toUser) {
        this.mToUser = toUser;
    }

    public String getRecentContent() {
        return mRecentContent;
    }

    public void setRecentContent(String recentContent) {
        this.mRecentContent = recentContent;
    }

    public long getRecentTime() {
        return mRecentTime;
    }

    public void setRecentTime(long recentTime) {
        this.mRecentTime = recentTime;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }
}