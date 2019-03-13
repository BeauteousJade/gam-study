package com.example.pby.gam_study.network.bean;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    private String mId;
    @SerializedName("content")
    private String mContent;
    @SerializedName("fromUser")
    private User mFromUser;
    @SerializedName("toUser")
    private User mToUser;
    @SerializedName("time")
    private long mTime;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public User getFromUser() {
        return mFromUser;
    }

    public void setFromUser(User fromUser) {
        this.mFromUser = fromUser;
    }

    public User getToUser() {
        return mToUser;
    }

    public void setToUser(User toUser) {
        this.mToUser = toUser;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }
}
