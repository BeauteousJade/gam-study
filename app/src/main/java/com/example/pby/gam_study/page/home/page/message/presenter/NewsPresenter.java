package com.example.pby.gam_study.page.home.page.message.presenter;

import android.view.View;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.post.PostActivity;

import butterknife.OnClick;

public class NewsPresenter extends Presenter {
    @Inject
    String mEmpty = null;

    @OnClick(R.id.post_container)
    public void onPostClick(View view) {
        PostActivity.startActivity(getCurrentActivity());
    }
}
