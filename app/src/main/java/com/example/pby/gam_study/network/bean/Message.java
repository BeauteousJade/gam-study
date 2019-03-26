package com.example.pby.gam_study.network.bean;

import java.util.Objects;

import androidx.annotation.Nullable;

public class Message {
    private String mFromUserId;
    private String mNewestContent;
    private int mUnReadCount;
    private long mTime;


    public String getFromUserId() {
        return mFromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.mFromUserId = fromUserId;
    }

    public String getNewestContent() {
        return mNewestContent;
    }

    public void setNewestContent(String newestContent) {
        this.mNewestContent = newestContent;
    }

    public int getUnReadCount() {
        return mUnReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.mUnReadCount = unReadCount;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Message) {
            return Objects.equals(mFromUserId, ((Message) obj).mFromUserId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mFromUserId);
    }
}
