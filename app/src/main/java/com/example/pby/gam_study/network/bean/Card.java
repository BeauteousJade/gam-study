package com.example.pby.gam_study.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Card implements Parcelable {

    @SerializedName("id")
    private String mId;
    @SerializedName("userId")
    private String mUserId;
    @SerializedName("kindId")
    private String mKindId;
    @SerializedName("oldImageUrl")
    private String mOldImageUrl;
    @SerializedName("editImageUrl")
    private String mEditImageUrl;
    @SerializedName("answer")
    private String mAnswer;
    @SerializedName("time")
    private long mTime;

    protected Card(Parcel in) {
        mId = in.readString();
        mUserId = in.readString();
        mKindId = in.readString();
        mOldImageUrl = in.readString();
        mEditImageUrl = in.readString();
        mAnswer = in.readString();
        mTime = in.readLong();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    public String getKindId() {
        return mKindId;
    }

    public void setKindId(String kindId) {
        this.mKindId = kindId;
    }

    public String getOldImageUrl() {
        return mOldImageUrl;
    }

    public void setOldImageUrl(String oldImageUrl) {
        this.mOldImageUrl = oldImageUrl;
    }

    public String getEditImageUrl() {
        return mEditImageUrl;
    }

    public void setEditImageUrl(String editImageUrl) {
        this.mEditImageUrl = editImageUrl;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        this.mAnswer = answer;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mUserId);
        dest.writeString(mKindId);
        dest.writeString(mOldImageUrl);
        dest.writeString(mEditImageUrl);
        dest.writeString(mAnswer);
        dest.writeLong(mTime);
    }
}
