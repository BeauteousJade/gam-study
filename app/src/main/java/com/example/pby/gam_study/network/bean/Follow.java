package com.example.pby.gam_study.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.Nullable;

public class Follow implements Parcelable {

    @SerializedName("id")
    private String mId;
    @SerializedName("fromUserId")
    private String mFromUserId;
    @SerializedName("toUserId")
    private String mToUserId;
    @SerializedName("time")
    private long mTime;

    protected Follow(Parcel in) {
        mId = in.readString();
        mFromUserId = in.readString();
        mToUserId = in.readString();
        mTime = in.readLong();
    }

    public static final Creator<Follow> CREATOR = new Creator<Follow>() {
        @Override
        public Follow createFromParcel(Parcel in) {
            return new Follow(in);
        }

        @Override
        public Follow[] newArray(int size) {
            return new Follow[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getFromUserId() {
        return mFromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.mFromUserId = fromUserId;
    }

    public String getToUserId() {
        return mToUserId;
    }

    public void setToUserId(String toUserId) {
        this.mToUserId = toUserId;
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
        dest.writeString(mFromUserId);
        dest.writeString(mToUserId);
        dest.writeLong(mTime);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Follow) {
            return Objects.equals(mId, ((Follow) obj).mId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId);
    }
}