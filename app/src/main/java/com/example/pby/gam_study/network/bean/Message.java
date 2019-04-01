package com.example.pby.gam_study.network.bean;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    private String mId;
    @SerializedName("toUser")
    private User mToUser;
    @SerializedName("fromUser")
    private User mFromUser;
    @SerializedName("content")
    private String mContent;
    @SerializedName("sendUserId")
    private String mSendUserId;
    @SerializedName("time")
    private long mTime;


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public User getToUser() {
        return mToUser;
    }

    public void setToUser(User toUser) {
        this.mToUser = toUser;
    }

    public User getFromUser() {
        return mFromUser;
    }

    public void setFromUser(User fromUser) {
        this.mFromUser = fromUser;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    public String getSendUserId() {
        return mSendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.mSendUserId = sendUserId;
    }
}
