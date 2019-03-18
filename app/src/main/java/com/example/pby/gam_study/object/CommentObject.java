package com.example.pby.gam_study.object;

public class CommentObject {
    private Object mData;
    private int mPostPosition;

    public CommentObject(Object data, int postPosition) {
        this.mData = data;
        this.mPostPosition = postPosition;
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
}
