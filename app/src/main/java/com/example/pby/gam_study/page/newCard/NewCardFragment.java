package com.example.pby.gam_study.page.newCard;

import android.support.v7.widget.RecyclerView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;

import java.util.Arrays;
import java.util.List;

public class NewCardFragment extends RecyclerViewFragment {

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        // 第一个位置为null，表示当前选择的图片还未上传，显示默认图片
        // 第二个位置用作占位符，表示显示title。
        // 第三个位置存储答案，默认还未设置答案。
        final List<String> dataList = Arrays.asList(null, "", "");
        return new NewCardAdapter(dataList);
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_title;
    }
}
