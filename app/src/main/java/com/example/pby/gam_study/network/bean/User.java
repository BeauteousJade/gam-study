package com.example.pby.gam_study.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.pby.gam_study.other.Diff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;

public class User implements Parcelable, Diff {

    public static final String HEAD = "head";
    public static final String NICK_NAME = "nickName";
    public static final String FOLLOW = "follow";
    public static final String FANS = "fans";
    public static final String SCORE = "score";

    private String id;
    private String head;
    private String token;
    private String nickName;
    private List<Follow> followUserList;
    private List<Follow> fansUserList;
    private int score;

    protected User(Parcel in) {
        id = in.readString();
        head = in.readString();
        token = in.readString();
        nickName = in.readString();
        score = in.readInt();
        followUserList = new ArrayList<>();
        fansUserList = new ArrayList<>();
        in.readList(followUserList, getClass().getClassLoader());
        in.readList(fansUserList, getClass().getClassLoader());
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

    public List<Follow> getFollowUserList() {
        return followUserList;
    }

    public void setFollowUserList(List<Follow> followUserList) {
        this.followUserList = followUserList;
    }

    public List<Follow> getFansUserList() {
        return fansUserList;
    }

    public void setFansUserList(List<Follow> fansUserList) {
        this.fansUserList = fansUserList;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
        dest.writeInt(score);
        dest.writeList(followUserList);
        dest.writeList(fansUserList);
    }


    @Override
    public boolean areItemsTheSame(Diff diff) {
        return equals(diff);
    }

    @Override
    public boolean onContentTheme(Diff diff) {
        if (diff instanceof User) {
            final User user = (User) diff;
            return Objects.equals(head, user.getHead()) && Objects.equals(nickName, user.getNickName()) && Objects.equals(followUserList, user.getFollowUserList()) && Objects.equals(fansUserList, user.getFansUserList());
        }
        return false;
    }

    @Override
    public Object getChangePayload(Diff diff) {
        if (diff instanceof User) {
            StringBuilder stringBuilder = new StringBuilder();
            User user = (User) diff;
            if (!Objects.equals(head, user.getHead())) {
                stringBuilder.append(HEAD);
            }
            if (!Objects.equals(nickName, user.getNickName())) {
                stringBuilder.append(NICK_NAME);
            }
            if (!Objects.equals(followUserList, user.getFollowUserList())) {
                stringBuilder.append(FOLLOW);
            }
            if (!Objects.equals(fansUserList, user.getFansUserList())) {
                stringBuilder.append(FANS);
            }
            if (!Objects.equals(score, score)) {
                stringBuilder.append(SCORE);
            }
            return stringBuilder.toString();
        }
        return null;
    }
}
