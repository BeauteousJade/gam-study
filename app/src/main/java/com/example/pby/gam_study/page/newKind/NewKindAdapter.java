package com.example.pby.gam_study.page.newKind;

import android.support.annotation.NonNull;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.adapter.base.BaseViewHolder;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.newKind.presenter.NewKindItemPresenter;

import java.util.List;

public class NewKindAdapter extends BaseRecyclerAdapter<NewKindItem> {

    private static final int TYPE_EDIT = 0;
    private static final int TYPE_COVER = 1;
    private static final int TYPE_TITLE = 2;
    private NewKindObservable mNewKindObservable;

    public NewKindAdapter(List<NewKindItem> dataList, NewKindObservable observable) {
        super(dataList);
        mNewKindObservable = observable;
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        if (viewType == TYPE_EDIT) {
            return R.layout.item_kind_name;
        } else if (viewType == TYPE_TITLE) {
            return R.layout.item_kind_name_title;
        } else {
            return R.layout.item_kind_cover;
        }
    }

    @Override
    public int getItemViewTypeNoEmpty(int position) {
        if (position == 0) {
            return TYPE_EDIT;
        } else if (position == 1) {
            return TYPE_TITLE;
        } else {
            return TYPE_COVER;
        }
    }

    @Override
    public int getItemStablePosition() {
        return 1;
    }

    @Override
    public Object onCreateContext(@NonNull BaseViewHolder baseViewHolder, int position, List<Object> payloads) {
        Context context = new Context();
        context.mContext = (BaseRecyclerAdapter.Context) super.onCreateContext(baseViewHolder, position, payloads);
        context.mNewKindObservable = mNewKindObservable;
        return context;
    }

    @Override
    protected Presenter onCreatePresenter() {
        Presenter presenter = new Presenter();
        presenter.add(new NewKindItemPresenter());
        return presenter;
    }

    public static class Context {
        @Provides(AccessIds.OBSERVABLE)
        public NewKindObservable mNewKindObservable = new NewKindObservable();
        @Provides(deepProvides = true)
        public BaseRecyclerAdapter.Context mContext;
    }
}
