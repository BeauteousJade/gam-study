package com.example.pby.gam_study.page.home.page.home.presenter;

import com.example.annation.Module;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.home.page.home.request.KindRequest;

@Module(RecyclerViewFragment.Context.class)
public class LoadKindPresenter extends Presenter {

    private KindRequest mKindRequest = new KindRequest();

    @Override
    protected void onBind() {
        mKindRequest.cancel();
        mKindRequest.enqueue(response -> {

        });
    }

    @Override
    protected void onDestroy() {
        mKindRequest.cancel();
    }
}
