package com.example.pby.gam_study.page.post.item;

import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.post.item.base.BasePostFragment;
import com.example.pby.gam_study.page.post.request.FollowRequest;

public class FollowFragment extends BasePostFragment {

    public static FollowFragment newInstance() {
        return new FollowFragment();
    }

    @Override
    public Request onCreateRequest() {
        return new FollowRequest();
    }
}
