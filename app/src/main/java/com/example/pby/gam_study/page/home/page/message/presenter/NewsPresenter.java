package com.example.pby.gam_study.page.home.page.message.presenter;

import android.view.View;

import com.example.annation.Module;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.home.page.message.MessagePageFragment;
import com.example.pby.gam_study.page.post.PostActivity;

import butterknife.OnClick;

@Module(MessagePageFragment.Context.class)
public class NewsPresenter extends Presenter {

    @OnClick(R.id.post_container)
    public void onPostClick(View view) {
        PostActivity.startActivity(getCurrentActivity());
    }
}
