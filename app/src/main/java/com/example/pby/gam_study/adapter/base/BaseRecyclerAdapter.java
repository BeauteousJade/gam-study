package com.example.pby.gam_study.adapter.base;

import android.content.res.Resources;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 如果{@link BaseRecyclerAdapter#mDataList} size = 1，相应位置的值为null,并且{@link BaseRecyclerAdapter#supportEmpty()}方法
 * 返回值为true，表示当前RecyclerView为空数据此时有特殊含义。但是，如果size = 0的位置为null，size大于1，此时没有特殊含义，让每个
 * 子类自我实现。
 *
 * @param <U>
 */
public abstract class BaseRecyclerAdapter<U> extends RecyclerView.Adapter<BaseViewHolder> implements Presence, ItemTouchStatus {

    public static final int TYPE_EMPTY = -1;

    protected List<U> mDataList;
    private BaseFragment mFragment;
    private BaseActivity mActivity;
    private Observable mObservable;
    private RecyclerView mRecyclerView;
    private Map<String, Object> mExtraMap = new HashMap<>();

    public BaseRecyclerAdapter(List<U> dataList) {
        mDataList = dataList;
    }

    public void addItemList(List<U> newDataList) {
        addItemList(mDataList.size(), newDataList);
    }

    public void addItemList(int index, List<U> newDataList) {
        // 空数据显示
        if (supportEmpty() && ((mDataList.size() == 1 && mDataList.get(0) == null) ||
                (mDataList.size() == getItemStablePosition() + 1 && ArrayUtil.isEmpty(newDataList)))) {
            mDataList.clear();
            index = 0;
            notifyItemRemoved(0);
        }
        mDataList.addAll(index, newDataList);
        notifyItemRangeInserted(index, newDataList.size());
    }

    public void addItem(int index, U u) {
        addItemList(index, Collections.singletonList(u));
    }

    public void addItem(U u) {
        addItemList(Collections.singletonList(u));
    }

    public void remove(U u) {
        final int position = mDataList.indexOf(u);
        if (position != -1) {
            remove(position);
        }
    }

    public void remove(int index) {
        mDataList.remove(index);
        notifyItemRemoved(index);
    }

    public void setItemList(List<U> newDatList) {
        if (ArrayUtil.isEmpty(newDatList)) {
            if (supportEmpty()) {
                setEmptyItemList();
            }
            return;
        }
        final U item = newDatList.get(0);
        if (item instanceof Diff) {
            for (int i = 0; i <= getItemStablePosition(); i++) {
                newDatList.add(i, mDataList.get(i));
            }
            mDataList.subList(getItemStablePosition() + 1, mDataList.size());
            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallback<>(mDataList, newDatList), false);
            mDataList.clear();
            mDataList.addAll(newDatList);
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
        holder.mPresenter.bind(onCreateContext(holder, position, payloads), mExtraMap, holder.itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        baseViewHolder.mPresenter.unBind();
        baseViewHolder.mPresenter.bind(onCreateContext(baseViewHolder, position, null), mExtraMap, baseViewHolder.itemView);
    }

    public void putExtra(String id, Object object) {
        mExtraMap.put(id, object);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        holder.mPresenter.unBind();
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
        context.mViewHolder = baseViewHolder;
        context.mRecyclerView = mRecyclerView;
        return context;
    }

    @Override
    public boolean onItemRemove(int position) {
        mDataList.remove(position);
        // 这里使用notifyDataSetChanged方法，避免remove时触发remove动画
        notifyDataSetChanged();
        return true;
    }

    @Override
    public void onSaveItemStatus(RecyclerView.ViewHolder viewHolder) {

    }

    protected boolean supportEmpty() {
        return true;
    }

    public boolean isShowEmpty() {
        return mDataList.size() == 1 && mDataList.get(0) == null;
    }

    protected int getEmptyLayoutId() {
        return R.layout.layout_view_empty;
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

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
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
        public List<?> mPayLoad;
        @Provides(AccessIds.OBSERVABLE)
        public Observable mObservable;
        @Provides(AccessIds.VIEW_HOLDER)
        public BaseViewHolder mViewHolder;
        @Provides(AccessIds.RECYCLER_VIEW)
        public RecyclerView mRecyclerView;
    }

    public final int getItemViewLayoutIfEmpty(int viewType) {
        if (isShowEmpty() && supportEmpty()) {
            return getEmptyLayoutId();
        }
        return getItemViewLayoutNoEmpty(viewType);
    }

    @Override
    public final int getItemViewType(int position) {
        if (isShowEmpty() && supportEmpty()) {
            return TYPE_EMPTY;
        }
        return getItemViewTypeNoEmpty(position);
    }

    public final Presenter onCreatePresenter(int viewType) {
        if (isShowEmpty() && supportEmpty()) {
            return new Presenter();
        }
        return onCreatePresenterIfNoEmpty(viewType);
    }

    public int getItemViewTypeNoEmpty(int position) {
        return 0;
    }

    public abstract int getItemViewLayoutNoEmpty(int viewType);


    protected abstract Presenter onCreatePresenterIfNoEmpty(int viewType);


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
            final T newItem = mNewList.get(newItemPosition);
            if (oldItem instanceof Diff && newItem instanceof Diff) {
                return ((Diff) oldItem).areItemsTheSame((Diff) newItem);
            }
            return Objects.equals(mOldList.get(oldItemPosition), newItem);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            final T oldItem = mOldList.get(oldItemPosition);
            final T newItem = mNewList.get(newItemPosition);
            if (oldItem instanceof Diff && newItem instanceof Diff) {
                return ((Diff) oldItem).onContentTheme((Diff) newItem);
            }
            return Objects.equals(mOldList.get(oldItemPosition), newItem);
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            final T oldItem = mOldList.get(oldItemPosition);
            final T newItem = mNewList.get(newItemPosition);
            if (oldItem instanceof Diff && newItem instanceof Diff) {
                return ((Diff) oldItem).getChangePayload((Diff) newItem);
            }
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }

}
