package com.example.pby.gam_study.adapter.base;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.mvp.Presence;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.other.Diff;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.widget.layoutManager.ItemTouchStatus;

import java.util.List;
import java.util.Objects;

/**
 * 如果{@link BaseRecyclerAdapter#mDataList} size = 1，并且相应位置的值为null，表示当前RecyclerView为空数据
 * 此时有特殊含义。但是，如果size = 0的位置为null，size大于1，此时没有特殊含义，让每个子类自我实现。
 *
 * @param <U>
 */
public abstract class BaseRecyclerAdapter<U> extends RecyclerView.Adapter<BaseViewHolder> implements Presence, ItemTouchStatus {

    private static final int TYPE_EMPTY = -1;

    protected List<U> mDataList;
    private BaseFragment mFragment;
    private BaseActivity mActivity;
    private Observable mObservable;

    public BaseRecyclerAdapter(List<U> dataList) {
        mDataList = dataList;
    }

    public void addItemList(List<U> newDataList) {
        addItemList(mDataList.size(), newDataList);
    }

    public void addItemList(int index, List<U> newDataList) {
        // 空数据显示
        if ((mDataList.size() == 1 && mDataList.get(0) == null) ||
                (mDataList.size() == getItemStablePosition() + 1 && ArrayUtil.isEmpty(newDataList))) {
            mDataList.clear();
            index = 0;
        }
        final int fromPosition = mDataList.size();
        mDataList.addAll(index, newDataList);
        notifyItemRangeChanged(fromPosition, newDataList.size());
    }

    public void setItemList(List<U> newDatList) {
        if (ArrayUtil.isEmpty(newDatList)) {
            setEmptyItemList();
            return;
        }
        final U item = newDatList.get(0);
        if (item instanceof Diff) {
            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallback<>(mDataList, newDatList));
            diffResult.dispatchUpdatesTo(this);
        } else {
            for (int i = mDataList.size() - 1; i >= getItemStablePosition() + 1; i--) {
                mDataList.remove(i);
            }
            mDataList.addAll(newDatList);
            notifyDataSetChanged();
        }
    }

    public void setEmptyItemList() {
        mDataList.clear();
        mDataList.add(null);
        notifyDataSetChanged();
    }

    public void setData(int index, U u, boolean notifyDataSetChanged) {
        if (index >= 0 && mDataList.size() > index) {
            mDataList.set(index, u);
            if (!notifyDataSetChanged) {
                notifyItemChanged(index);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    public int getItemStablePosition() {
        return -1;
    }

    public List<U> getDataList() {
        return mDataList;
    }

    public U getItem(int index) {
        return mDataList.get(index);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(getItemViewLayoutIfEmpty(viewType), viewGroup, false);
        final Presenter presenter = onCreatePresenter(viewType);
        presenter.create(this);
        return new BaseViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (ArrayUtil.isEmpty(payloads)) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            onPayloadsNotEmpty(holder, position, payloads);
        }
    }

    protected void onPayloadsNotEmpty(BaseViewHolder holder, int position, List<Object> payloads) {
        holder.mPresenter.unBind();
        holder.mPresenter.bind(onCreateContext(holder, position, payloads), holder.itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        if (!isShowEmpty()) {
            baseViewHolder.mPresenter.unBind();
            baseViewHolder.mPresenter.bind(onCreateContext(baseViewHolder, position, null), baseViewHolder.itemView);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        if (isShowEmpty()) {
            holder.mPresenter.unBind();
        }
    }

    public void onDestroy(RecyclerView recyclerView) {
        if (isShowEmpty()) {
            return;
        }
        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = recyclerView.getChildAt(i);
            final BaseViewHolder viewHolder = (BaseViewHolder) recyclerView.getChildViewHolder(child);
            viewHolder.mPresenter.destroy();
        }
    }


    public Object onCreateContext(@NonNull BaseViewHolder baseViewHolder, int position, List<Object> payloads) {
        Context context = new Context();
        context.mItem = mDataList.get(position);
        context.mPosition = position;
        context.mItemView = baseViewHolder.itemView;
        context.mAdapter = this;
        context.mPayLoad = payloads;
        context.mObservable = mObservable;
        return context;
    }

    @Override
    public boolean onItemRemove(int position) {
        mDataList.remove(position);
        // 这里使用notifyDataSetChanged方法，避免remove时触发remove动画
        notifyDataSetChanged();
        return true;
    }


    public boolean isShowEmpty() {
        return mDataList.size() == 1 && mDataList.get(0) == null;
    }

    protected int getEmptyLayoutId() {
        return R.layout.view_empty;
    }


    public void setCurrentFragment(BaseFragment fragment) {
        mFragment = fragment;
    }

    public void setCurrentActivity(BaseActivity activity) {
        mActivity = activity;
    }

    public void setObservable(Observable observable) {
        mObservable = observable;
    }

    public static class Context {
        @Provides(AccessIds.ITEM_POSITION)
        public int mPosition;
        @Provides(AccessIds.ITEM_DATA)
        public Object mItem;
        @Provides(AccessIds.ITEM_VIEW)
        public View mItemView;
        @Provides(AccessIds.RECYCLER_ADAPTER)
        public RecyclerView.Adapter mAdapter;
        @Provides(AccessIds.PAYLOAD)
        public List<Object> mPayLoad;
        @Provides(AccessIds.OBSERVABLE)
        public Observable mObservable;
    }

    public final int getItemViewLayoutIfEmpty(int viewType) {
        if (isShowEmpty()) {
            return getEmptyLayoutId();
        }
        return getItemViewLayoutNoEmpty(viewType);
    }

    @Override
    public final int getItemViewType(int position) {
        if (isShowEmpty()) {
            return TYPE_EMPTY;
        }
        return getItemViewTypeNoEmpty(position);
    }

    public int getItemViewTypeNoEmpty(int position) {
        return 0;
    }

    public abstract int getItemViewLayoutNoEmpty(int viewType);


    protected abstract Presenter onCreatePresenter(int viewType);


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseFragment> T getCurrentFragment() {
        return (T) mFragment;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseActivity> T getCurrentActivity() {
        return (T) mActivity;
    }

    @Override
    public View getRootView() {
        return mFragment.getView();
    }

    @Override
    public Resources getCurrentResources() {
        return mFragment.getResources();
    }

    public static class DiffUtilCallback<T> extends DiffUtil.Callback {

        private List<T> mOldList;
        private List<T> mNewList;

        public DiffUtilCallback(List<T> oldList, List<T> newList) {
            mOldList = oldList;
            mNewList = newList;
        }

        @Override

        public int getOldListSize() {
            return mOldList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            final T oldItem = mOldList.get(oldItemPosition);
            if (oldItem instanceof Diff) {
                return ((Diff) oldItem).areItemsTheSame((Diff) mNewList.get(newItemPosition));
            }
            return Objects.equals(mOldList.get(oldItemPosition), mNewList.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            final T oldItem = mOldList.get(oldItemPosition);
            if (oldItem instanceof Diff) {
                return ((Diff) oldItem).onContentTheme((Diff) mNewList.get(newItemPosition));
            }
            return Objects.equals(mOldList.get(oldItemPosition), mNewList.get(newItemPosition));
        }
    }

}
