package com.example.pby.gam_study.network.bean;

import com.example.pby.gam_study.util.GsonUtil;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Comment {

    @SerializedName("id")
    private String mId;
    @SerializedName("postId")
    private String mPostId;
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

    public String getPostId() {
        return mPostId;
    }

    public void setPostId(String postId) {
        this.mPostId = postId;
    }

    @NonNull
    @Override
    public String toString() {
        return GsonUtil.toString(this);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Comment) {
            return Objects.equals(mId, ((Comment) obj).getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId);
    }
}
