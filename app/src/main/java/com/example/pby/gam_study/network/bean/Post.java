package com.example.pby.gam_study.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {

    @SerializedName("id")
    private String mId;
    @SerializedName("user")
    private User mUser;
    @SerializedName("content")
    private String mContent;
    @SerializedName("imageUrlList")
    private List<String> mImageUrlList;
    @SerializedName("likeUserList")
    private List<User> mLikeUserList;
    @SerializedName("commentList")
    private List<Comment> mCommentList;
    @SerializedName("mTime")
    private long mTime;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public List<String> getImageUrlList() {
        return mImageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.mImageUrlList = imageUrlList;
    }

    public List<User> getLikeUserList() {
        return mLikeUserList;
    }

    public void setLikeUserList(List<User> likeUserList) {
        this.mLikeUserList = likeUserList;
    }

    public List<Comment> getCommentList() {
        return mCommentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.mCommentList = commentList;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }
}
