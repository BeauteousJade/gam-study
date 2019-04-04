package com.example.pby.gam_study.page.kind;

import android.os.Bundle;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.home.page.home.item.AllKindFragment;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MineKindFragment extends BaseFragment {

    private static final String USER_ID = "user_id";

    private String mUserId;

    public static MineKindFragment newInstance(String userId) {
        MineKindFragment mineFragment = new MineKindFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USER_ID, userId);
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @Override
    protected void onPrepare() {
        mUserId = Objects.requireNonNull(getArguments()).getString(USER_ID);
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new MineKindTitleBarPresenter());
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, AllKindFragment.newInstance(mUserId));
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_kind;
    }
}
