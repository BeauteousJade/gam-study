package com.example.pby.gam_study.factory.experssion;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.adapter.base.BaseViewHolder;
import com.example.pby.gam_study.factory.experssion.presenter.LoadExpressionPresenter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Expression;

import java.util.List;

import androidx.annotation.NonNull;

public class ExpressionAdapter extends BaseRecyclerAdapter<Expression> {

    private ExpressionFragment.OnExpressionClickListener mOnExpressionClickListener;

    public ExpressionAdapter(List<Expression> dataList, ExpressionFragment.OnExpressionClickListener listener) {
        super(dataList);
        mOnExpressionClickListener = listener;
    }


    @Override
    public Object onCreateContext(@NonNull BaseViewHolder baseViewHolder, int position, List<Object> payloads) {
        Context context = new Context();
        context.mContext = (BaseRecyclerAdapter.Context) super.onCreateContext(baseViewHolder, position, payloads);
        context.mOnExpressionClickListener = mOnExpressionClickListener;
        return context;
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        return R.layout.item_expression;
    }

    @Override
    protected Presenter onCreatePresenter(int viewType) {
        Presenter presenter = new Presenter();
        presenter.add(new LoadExpressionPresenter());
        return presenter;
    }


    public static class Context {
        @Provides(deepProvides = true)
        public BaseRecyclerAdapter.Context mContext;
        @Provides(AccessIds.LISTENER)
        public ExpressionFragment.OnExpressionClickListener mOnExpressionClickListener;
    }
}
