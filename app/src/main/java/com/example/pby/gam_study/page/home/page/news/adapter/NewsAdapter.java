package com.example.pby.gam_study.page.home.page.news.adapter;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;

import java.util.List;

public class NewsAdapter extends BaseRecyclerAdapter<Post> {

    public NewsAdapter(List<Post> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_news;
    }

    @Override
    protected Presenter onCreatePresenter(int viewType) {
        return new Presenter();
    }
}
