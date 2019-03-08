package com.example.pby.gam_study.page.newCard;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.newCard.presenter.NewCardTitleBarPresenter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NewCardFragment extends RecyclerViewFragment {

    public static final String KEY_OBSERVABLE_ANSWER = "key_observable_answer";
    public static final String KEY_OBSERVABLE_OLD_IMAGE = "KEY_OBSERVABLE_OLD_IMAGE";
    public static final String KEY_OBSERVABLE_EDIT_IMAGE = "key_observable_edit_image";

    private String mKindId;

    public static NewCardFragment newInstance(String kindId) {
        NewCardFragment newCardFragment = new NewCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NewCardActivity.KIND_ID, kindId);
        newCardFragment.setArguments(bundle);
        return newCardFragment;
    }

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        mKindId = Objects.requireNonNull(getArguments()).getString(NewCardActivity.KIND_ID);
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        // 第一个位置为null，表示当前选择的图片还未上传，显示默认图片。
        // 第二个位置用作占位符，表示显示title。
        // 第三个位置存储答案，默认还未设置答案。
        final List<String> dataList = Arrays.asList(null, "", "");
        return new NewCardAdapter(dataList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mContext = super.onCreateBaseContext();
        context.mKindId = mKindId;
        return (T) context;
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }

    @Override
    public Presenter onCreatePresenter() {
        final Presenter presenter = super.onCreatePresenter();
        presenter.add(new NewCardTitleBarPresenter());
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview_event_title;
    }

    public static class Context {
        @Provides(deepProvides = true)
        public RecyclerViewFragment.Context mContext;
        @Provides(AccessIds.KIND_ID)
        public String mKindId;
    }
}
