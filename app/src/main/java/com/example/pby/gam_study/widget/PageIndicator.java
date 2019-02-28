package com.example.pby.gam_study.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageIndicator extends RecyclerView {

    private Drawable mDrawable;
    private int offset;
    private Rect mChildRect;
    private int mAnchorViewId;
    private View mAnchorView;
    private int mHorizontalPadding;
    private int mVerticalPadding;
    private final OnScrollListener mAttachRecyclerViewOnScrollListener = new OnScrollListener() {

        private int mScrollX = 0;

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            mScrollX += dx;
            final float percent = getChildAt(0).getMeasuredWidth() * 1.0f / recyclerView.getMeasuredWidth();
            addScrollX((int) (mScrollX * percent));
        }
    };

    public PageIndicator(Context context) {
        super(context);
        initialize();
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setAdapter(new PageAdapter(new ArrayList<>()));
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        setItemAnimator(null);
    }

    public void setSelectedDrawable(@DrawableRes int id) {
        mDrawable = getResources().getDrawable(id);
    }


    public void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(mAttachRecyclerViewOnScrollListener);
    }

    public void setTitleList(List<? extends TitleBean> titleList) {
        if (getAdapter() instanceof PageAdapter) {
            ((PageAdapter) getAdapter()).getTitleList().addAll(titleList);
            getAdapter().notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        if (getAdapter() instanceof PageAdapter) {
            ((PageAdapter) getAdapter()).setOnItemClickListener(listener);
        }
    }

    public void setAnchorViewId(int id) {
        mAnchorViewId = id;
    }

    public void setHorizontalPadding(int padding) {
        mHorizontalPadding = padding;
    }

    public void setVerticalPadding(int padding) {
        mVerticalPadding = padding;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mDrawable != null) {
            if (mChildRect == null) {
                mChildRect = new Rect();
                mAnchorView = findViewById(mAnchorViewId);
                final View child;
                if (mAnchorView != null) {
                    child = mAnchorView;
                } else {
                    child = getChildCount() != 0 ? getChildAt(0) : null;
                }
                if (child != null) {
                    mChildRect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                }
            }
            mDrawable.setBounds(mChildRect.left + offset - mHorizontalPadding, mChildRect.top - mVerticalPadding,
                    mChildRect.right + offset + mHorizontalPadding, mChildRect.bottom + mVerticalPadding);
            mDrawable.draw(canvas);
        }
        super.draw(canvas);
    }

    private void addScrollX(int scrollX) {
        // TODO 执行滑动
        offset = scrollX;
        invalidate();
    }

    public static class PageAdapter extends Adapter<PageAdapter.PageViewHolder> {
        private List<TitleBean> mTitleList;
        private OnItemClickListener mOnItemClickListener;

        public PageAdapter(List<TitleBean> titleList) {
            mTitleList = titleList;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public PageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new PageViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.title_item_view, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final PageViewHolder pageViewHolder, int i) {
            final TitleBean titleBean = mTitleList.get(i);
            final View itemView = pageViewHolder.itemView;
            pageViewHolder.mTextView.setText(titleBean.mTitle);
            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(pageViewHolder.getAdapterPosition());
                }
            });
            if (titleBean.mSelected) {
                pageViewHolder.mTextView.setTextColor(ResourcesUtil.getColor(itemView.getContext(), R.color.color_item_selected_white));
            } else {
                pageViewHolder.mTextView.setTextColor(ResourcesUtil.getColor(itemView.getContext(), R.color.color_item_dark));
            }
            if (titleBean.mHasNotify) {
                pageViewHolder.mDotView.setVisibility(View.VISIBLE);
            } else {
                pageViewHolder.mDotView.setVisibility(View.GONE);
            }
        }

        public void setSelect(boolean selected, int position) {
            if (!ArrayUtil.isEmpty(mTitleList) && mTitleList.size() > position) {
                for (TitleBean titleBean : mTitleList) {
                    titleBean.setSelected(false);
                }
                mTitleList.get(position).setSelected(selected);
                notifyDataSetChanged();
            }
        }

        public void setHashNotify(boolean hashNotify, int position) {
            if (!ArrayUtil.isEmpty(mTitleList) && mTitleList.size() > position) {
                mTitleList.get(position).setHasNotify(hashNotify);
                notifyItemChanged(position);
            }
        }

        public List<TitleBean> getTitleList() {
            return mTitleList;
        }

        @Override
        public int getItemCount() {
            return mTitleList.size();
        }

        public class PageViewHolder extends ViewHolder {
            @BindView(R.id.title)
            TextView mTextView;

            @BindView(R.id.dot)
            View mDotView;

            public PageViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public static class TitleBean {
        private String mTitle;
        private boolean mSelected;
        private boolean mHasNotify;

        public TitleBean(String title) {
            this.mTitle = title;
        }

        public TitleBean(String title, boolean selected) {
            this(title);
            mSelected = selected;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            this.mTitle = title;
        }

        public boolean getSelected() {
            return mSelected;
        }

        public void setSelected(boolean selected) {
            this.mSelected = selected;
        }

        public void setHasNotify(boolean hasNotify) {
            mHasNotify = hasNotify;
        }

        public boolean hasNotify() {
            return mHasNotify;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
