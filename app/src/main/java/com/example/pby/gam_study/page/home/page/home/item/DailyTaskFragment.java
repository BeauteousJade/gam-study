package com.example.pby.gam_study.page.home.page.home.item;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.DailyTaskItemDecoration;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.network.bean.DailyTask;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.page.home.page.home.adapter.DailyTaskAdapter;
import com.example.pby.gam_study.page.home.page.home.request.DailyTaskRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DailyTaskFragment extends RefreshRecyclerViewFragment {

    public static DailyTaskFragment newInstance() {
        return new DailyTaskFragment();
    }


    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
    }

    @Override
    public Request onCreateRequest() {
        return new DailyTaskRequest();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily_task;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        List<DailyTask> dailyTasks = new ArrayList<>();
        dailyTasks.add(null);
        return new DailyTaskAdapter(dailyTasks);
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new DailyTaskItemDecoration(requireActivity()));
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireActivity());
    }
}
