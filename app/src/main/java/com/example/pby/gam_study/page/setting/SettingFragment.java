package com.example.pby.gam_study.page.setting;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.LinearLayoutManagerVerticalItemDecoration;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Setting;
import com.example.pby.gam_study.page.setting.presenter.LoadConfigPresenter;
import com.example.pby.gam_study.page.setting.presenter.SettingTitleBarPresenter;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SettingFragment extends RecyclerViewFragment {

    public static final String NOTIFICATION = "notification";
    public static final String RINGTONE = "ringtone";


    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview_title;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new LoadConfigPresenter());
        presenter.add(new SettingTitleBarPresenter());
        return presenter;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        List<Object> dataList = new ArrayList<>();
        dataList.add(new Setting("开启通知", false));
        dataList.add(new Setting("开启铃声", false));
        return new SettingAdapter(dataList);
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new LinearLayoutManagerVerticalItemDecoration(ResourcesUtil.getColor(requireContext(),
                R.color.bg_color), DisplayUtil.dpToPx(requireContext(), 0.5f)));
    }
}
