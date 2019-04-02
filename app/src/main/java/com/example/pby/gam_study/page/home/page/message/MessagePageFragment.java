package com.example.pby.gam_study.page.home.page.message;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.decoration.LinearLayoutManagerVerticalItemDecoration;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.other.GamItemTouchCallback;
import com.example.pby.gam_study.page.home.page.HomePage;
import com.example.pby.gam_study.page.home.page.message.presenter.MessageObserverPresenter;
import com.example.pby.gam_study.page.home.page.message.presenter.NewsPresenter;
import com.example.pby.gam_study.page.home.page.message.request.MessageItemRequest;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.widget.TitleBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MessagePageFragment extends RefreshRecyclerViewFragment implements HomePage {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    public static MessagePageFragment newInstance() {
        return new MessagePageFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_message;
    }

    @Override
    public Request onCreateRequest() {
        return new MessageItemRequest();
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new MessageAdapter(new ArrayList<>());
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new NewsPresenter());
        presenter.add(new MessageObserverPresenter());
        return presenter;
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }

    @Override
    protected List<? extends RecyclerView.ItemDecoration> onCreateItemDecoration() {
        return Collections.singletonList(new LinearLayoutManagerVerticalItemDecoration(1, ResourcesUtil.getColor(requireContext(), R.color.bg_color)
                , DisplayUtil.dpToPx(requireContext(), 5)));
    }

    @Override
    public String getTitle() {
        return ResourcesUtil.getString(requireContext(), R.string.title_message);
    }

    @Override
    public ItemTouchHelper.Callback onCreateCallback() {
        return new GamItemTouchCallback(getRecyclerAdapter(), DisplayUtil.dpToPx(requireContext(), 100));
    }

    @Override
    public void onPageSelect() {
        mTitleBar.setTitle(getTitle());
        mTitleBar.setLeftIcon(getLeftIcon());
        mTitleBar.setRightIcon(getRightIcon());
    }

    @Override
    public void onPageUnSelect() {

    }
}
