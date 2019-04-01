package com.example.pby.gam_study.network.bean;

import com.example.pby.gam_study.other.Diff;
import com.example.pby.gam_study.util.StringUtil;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class MessageItem implements Diff {

    public static final String CONTENT = "content";
    public static final String TIME = "time";

    @SerializedName("id")
    private String mId;
    @SerializedName("fromUser")
    private User mFromUser;
    @SerializedName("toUser")
    private User mToUser;
    @SerializedName("recentContent")
    private String mRecentContent;
    @SerializedName("fromUserUnReadCount")
    private int mFromUserUnReadCount;
    @SerializedName("toUserUnReadCount")
    private int mToUserUnReadCount;
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

    public User getToUser() {
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

    public int getFromUserUnReadCount() {
        return mFromUserUnReadCount;
    }

    public void setFromUserUnReadCount(int fromUserUnReadCount) {
        this.mFromUserUnReadCount = fromUserUnReadCount;
    }

    public int getToUserUnReadCount() {
        return mToUserUnReadCount;
    }

    public void setToUserUnReadCount(int toUserUnReadCount) {
        this.mToUserUnReadCount = toUserUnReadCount;
    }

    @Override
    public boolean areItemsTheSame(Diff diff) {
        if (diff instanceof MessageItem) {
            return Objects.equals(mId, ((MessageItem) diff).mId);
        }
        return false;
    }

    @Override
    public boolean onContentTheme(Diff diff) {
        if (diff instanceof MessageItem) {
            return Objects.equals(mRecentContent, ((MessageItem) diff).mRecentContent);
        }
        return false;
    }

    @Override
    public Object getChangePayload(Diff diff) {
        if (diff instanceof MessageItem) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!Objects.equals(mRecentContent, ((MessageItem) diff).mRecentContent)) {
                stringBuilder.append(CONTENT);
            }
            if (!Objects.equals(mTime, ((MessageItem) diff).mTime)) {
                stringBuilder.append(TIME);
            }
            return StringUtil.emptyIfEmpty(stringBuilder.toString());
        }
        return null;
    }
}