package com.example.pby.gam_study.factory.experssion;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.factory.experssion.presenter.RequestExpressionPresenter;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ExpressionFragment extends RecyclerViewFragment {


    private OnExpressionClickListener mOnExpressionClickListener;


    public static ExpressionFragment newInstance() {
        return new ExpressionFragment();
    }


    public void setOnExpressionClickListener(OnExpressionClickListener listener) {
        mOnExpressionClickListener = listener;
    }

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        getRecyclerView().setItemViewCacheSize(100);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mContext = super.onCreateBaseContext();
        context.mOnExpressionClickListener = mOnExpressionClickListener;
        return (T) context;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new RequestExpressionPresenter());
        return presenter;
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        return new ExpressionAdapter(new ArrayList<>(), mOnExpressionClickListener);
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createGridLayoutManagerIfEmpty(requireContext(), getRecyclerAdapter(), 9, null);
    }


    public static class Context {
        @Provides(deepProvides = true)
        public RecyclerViewFragment.Context mContext;
        @Provides(AccessIds.LISTENER)
        public OnExpressionClickListener mOnExpressionClickListener;
    }


    @FunctionalInterface
    public interface OnExpressionClickListener {
        void onExpressionClick(String fileName);
    }
}
