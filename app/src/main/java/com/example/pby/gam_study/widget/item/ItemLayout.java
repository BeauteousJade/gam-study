package com.example.pby.gam_study.widget.item;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.IntRange;
import android.support.annotation.Size;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.pby.gam_study.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemLayout extends ViewGroup {

    private Adapter mAdapter;
    private OnItemClickListener mOnTabClickListener;
    private final List<ItemViewHolder> mViewHolderList = new ArrayList<>();
    protected List<ItemDecoration> mItemDecorationList = new ArrayList<>();

    private final AdapterObserver mTabAdapterObserver = new AdapterObserver() {
        @Override
        public void onItemChanged(int position) {
            requestLayout();
        }

        @Override
        public void onDataSetChanged() {
            requestLayout();
        }
    };

    public ItemLayout(Context context) {
        super(context);
        initialize();
    }


    public ItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAdapter != null && mAdapter.getItemCount() != 0) {
            addChildren();
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                final int[] itemOffsets = getItemOffsets(i);
                measureChildWithMargins(child, widthMeasureSpec, itemOffsets[0] + itemOffsets[2], heightMeasureSpec, itemOffsets[1] + itemOffsets[3]);
                ((LayoutParams) child.getLayoutParams()).mItemOffsets = itemOffsets;
            }
            if (interceptMeasure(widthMeasureSpec, heightMeasureSpec)) {
                return;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int[] getItemOffsets(int position) {
        int[] itemOffsets = new int[4];
        if (!ArrayUtil.isEmpty(mItemDecorationList)) {
            for (ItemDecoration itemDecoration : mItemDecorationList) {
                final int[] offset = itemDecoration.getItemOffset(this, position);
                itemOffsets[0] += offset[0];
                itemOffsets[1] += offset[1];
                itemOffsets[2] += offset[2];
                itemOffsets[3] += offset[3];
            }
        }
        return itemOffsets;
    }


    protected boolean interceptMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        return false;
    }

    @SuppressWarnings("unchecked")
    private void addChildren() {
        removeAllViews();
        final int itemCount = mAdapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            final ItemViewHolder viewHolder = getTabViewHolderFromList(i);
            viewHolder.setPosition(i);
            addView(viewHolder.getItemView());
            viewHolder.getItemView().setOnClickListener(v -> {
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.onItemClick(viewHolder.getPosition());
                }
            });
            mAdapter.onBindViewHolder(viewHolder, i);
            mViewHolderList.add(viewHolder);
        }
    }

    private ItemViewHolder getTabViewHolderFromList(int position) {
        for (ItemViewHolder viewHolder : mViewHolderList) {
            if (viewHolder.getPosition() == position) {
                mViewHolderList.remove(position);
                return viewHolder;
            }
        }
        return mAdapter.onCreateViewHolder(this);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public void setOnTabClickListener(OnItemClickListener onTabClickListener) {
        mOnTabClickListener = onTabClickListener;
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mAdapter.removeTabAdapterObserver(mTabAdapterObserver);
        requestLayout();
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        if (itemDecoration != null) {
            mItemDecorationList.add(itemDecoration);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private void initialize() {
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public abstract static class AdapterObserver {
        public abstract void onItemChanged(int position);

        public abstract void onDataSetChanged();
    }

    public abstract static class Adapter<T extends ItemViewHolder> {

        private List<AdapterObserver> mObserves = new ArrayList<>();

        public abstract int getItemCount();

        public abstract T onCreateViewHolder(ViewGroup parent);

        public abstract void onBindViewHolder(T t, int position);

        public void notifyItemChanged(int position) {
            for (int i = 0; i < mObserves.size(); i++) {
                mObserves.get(0).onItemChanged(position);
            }
        }

        public void notifyDataSetChanged() {
            for (int i = 0; i < mObserves.size(); i++) {
                mObserves.get(0).onDataSetChanged();
            }
        }

        public void addTabAdapterObserver(AdapterObserver observer) {
            mObserves.add(observer);
        }

        public void removeTabAdapterObserver(AdapterObserver observer) {
            mObserves.remove(observer);
        }
    }

    public static class ItemDecoration {
        public void onDraw(Canvas canvas) {
        }

        public int[] getItemOffset(ItemLayout parent, int position) {
            return new int[4];
        }
    }

    public static class LayoutParams extends MarginLayoutParams {

        public int[] mItemOffsets;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

}
