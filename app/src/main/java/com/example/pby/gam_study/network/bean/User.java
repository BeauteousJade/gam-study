package com.example.pby.gam_study.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import androidx.annotation.Nullable;

public class User implements Parcelable {

    private String id;
    private String head;
    private String token;
    private String nickName;

    protected User(Parcel in) {
        id = in.readString();
        head = in.readString();
        token = in.readString();
        nickName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof User) {
            return Objects.equals(id, ((User) obj).getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(head);
        dest.writeString(token);
        dest.writeString(nickName);
    }
}
