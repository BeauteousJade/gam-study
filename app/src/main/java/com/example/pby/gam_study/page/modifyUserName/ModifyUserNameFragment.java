package com.example.pby.gam_study.page.modifyUserName;

import android.os.Bundle;

import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.modifyUserName.presenter.ModifyUserTitleBarPresenter;

import java.util.Objects;

public class ModifyUserNameFragment extends BaseFragment {


    public static ModifyUserNameFragment newInstance(String userId) {
        ModifyUserNameFragment modifyUserNameFragment = new ModifyUserNameFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ModifyUserNameActivity.USER_ID, userId);
        modifyUserNameFragment.setArguments(bundle);
        return modifyUserNameFragment;
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        putExtra(AccessIds.USER_ID, Objects.requireNonNull(getArguments()).getString(ModifyUserNameActivity.USER_ID));
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new ModifyUserTitleBarPresenter());
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_name;
    }
}
