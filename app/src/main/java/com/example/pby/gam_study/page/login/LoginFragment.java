package com.example.pby.gam_study.page.login;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.login.presenter.LoginPresenter;

public class LoginFragment extends BaseFragment {

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new LoginPresenter());
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }
}
