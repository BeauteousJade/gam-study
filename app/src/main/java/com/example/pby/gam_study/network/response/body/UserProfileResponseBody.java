package com.example.pby.gam_study.network.response.body;

import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.bean.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfileResponseBody {

    @SerializedName("user")
    private User mUser;
    @SerializedName("postList")
    private List<Post> mPostList;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public List<Post> getPostList() {
        return mPostList;
    }

    public void setPostList(List<Post> postList) {
        this.mPostList = postList;
    }
}
