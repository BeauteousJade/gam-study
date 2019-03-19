package com.example.pby.gam_study.object;

public class CommentObject {
    private Object mData;
    private int mPostPosition;
    private String mPostId;

    public CommentObject(Object data, int postPosition, String postId) {
        this.mData = data;
        this.mPostPosition = postPosition;
        this.mPostId = postId;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }

    public int getPostPosition() {
        return mPostPosition;
    }

    public void setPostPosition(int postPosition) {
        this.mPostPosition = postPosition;
    }

    public void setPostId(String postId) {
        mPostId = postId;
    }

    public String getPostId() {
        return mPostId;
    }
}
