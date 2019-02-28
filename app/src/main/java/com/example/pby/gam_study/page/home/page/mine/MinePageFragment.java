package com.example.pby.gam_study.page.home.page.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.page.home.page.BaseHomePageFragment;
import com.example.pby.gam_study.util.ResourcesUtil;

public class MinePageFragment extends BaseHomePageFragment {

    private static final int LAYOUT_ID = R.layout.page_fragment_mine;

    public static MinePageFragment newInstance() {
        MinePageFragment pageFragment = new MinePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LAYOUT_ID, LAYOUT_ID);
        pageFragment.setArguments(bundle);
        return pageFragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(ResourcesUtil.getColor(requireContext(), R.color.color_002));
    }

    @Override
    public String getTitle() {
        return ResourcesUtil.getString(requireContext(), R.string.title_mine);
    }
}
