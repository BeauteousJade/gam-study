package com.example.pby.gam_study.page.post.item;

import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.post.item.base.BasePostFragment;
import com.example.pby.gam_study.page.post.request.FindRequest;

public class FindFragment extends BasePostFragment {

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public Request onCreateRequest() {
        return new FindRequest();
    }
}