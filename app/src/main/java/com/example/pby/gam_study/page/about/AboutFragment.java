package com.example.pby.gam_study.page.about;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.about.presenter.AboutItemPresenter;
import com.example.pby.gam_study.page.about.presenter.AboutTitleBarPresenter;

public class AboutFragment extends BaseFragment {

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }


    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new AboutTitleBarPresenter());
        presenter.add(new AboutItemPresenter());
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }
}
