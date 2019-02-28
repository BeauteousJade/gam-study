package com.example.pby.gam_study.fragment.dialog;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.DisplayUtil;

public class GamDialogFragment extends DialogFragment {

    private static final String KEY_STYLE = "key_style";
    private static final String KEY_LAYOUT_ID = "key_layout_id";

    @IntDef({LocationStyle.STYLE_LEFT_TOP, LocationStyle.STYLE_CENTER_TOP,
            LocationStyle.STYLE_RIGHT_TOP, LocationStyle.STYLE_LEFT_BOTTOM,
            LocationStyle.STYLE_CENTER_BOTTOM, LocationStyle.STYLE_RIGHT_BOTTOM})
    public @interface LocationStyle {
        int STYLE_LEFT_TOP = 1;
        int STYLE_CENTER_TOP = 1 << 1;
        int STYLE_RIGHT_TOP = 1 << 2;
        int STYLE_LEFT_BOTTOM = 1 << 3;
        int STYLE_CENTER_BOTTOM = 1 << 4;
        int STYLE_RIGHT_BOTTOM = 1 << 5;
    }

    @LocationStyle
    private int mLocationStyle;
    @LayoutRes
    private int mLayoutId;
    private View mAnchorView;

    private int mTranslationX;
    private int mTranslationY;

    private int[] mClickIds;
    private View.OnClickListener mOnClickListener;

    private static GamDialogFragment newInstance(@LocationStyle int style, @LayoutRes int layoutId) {
        final GamDialogFragment customDialogFragment = new GamDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_STYLE, style);
        bundle.putInt(KEY_LAYOUT_ID, layoutId);
        customDialogFragment.setArguments(bundle);
        return customDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        resolveBundle();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(mLayoutId, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final View contentView = view;
        contentView.getRootView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateTranslation(contentView);
                adjustLayout();
                contentView.getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        setViewClickListener(contentView);
    }

    private void setViewClickListener(View view) {
        if (mOnClickListener != null && !ArrayUtil.isEmpty(mClickIds)) {
            for (int id : mClickIds) {
                final View foundView = view.findViewById(id);
                if (foundView != null) {
                    foundView.setOnClickListener(mOnClickListener);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(null);
            window.setGravity(Gravity.START | Gravity.TOP);
        }
    }

    private void adjustLayout() {
        final Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.x = mTranslationX;
            lp.y = mTranslationY;
            window.setAttributes(lp);
        }
    }


    private void calculateTranslation(View contentView) {
        if (mAnchorView != null) {
            final int contentWidth = contentView.getMeasuredWidth();
            final int contentHeight = contentView.getMeasuredHeight();
            final int anchorWidth = mAnchorView.getMeasuredWidth();
            final int anchorHeight = mAnchorView.getMeasuredHeight();
            final int[] anchorLocation = new int[2];
            mAnchorView.getLocationInWindow(anchorLocation);
            mTranslationX = anchorLocation[0];
            mTranslationY = anchorLocation[1] - DisplayUtil.getStatusBarHeight(getContext());
            switch (mLocationStyle) {
                case LocationStyle.STYLE_LEFT_TOP:
                    mTranslationY -= contentHeight;
                    break;
                case LocationStyle.STYLE_CENTER_TOP:
                    mTranslationY -= contentHeight;
                    mTranslationX += anchorWidth / 2 - contentWidth / 2;
                    break;
                case LocationStyle.STYLE_RIGHT_TOP:
                    mTranslationY -= contentHeight;
                    mTranslationX += anchorWidth - contentWidth;
                    break;
                case LocationStyle.STYLE_LEFT_BOTTOM:
                    mTranslationY += anchorHeight;
                    break;
                case LocationStyle.STYLE_CENTER_BOTTOM:
                    mTranslationY += anchorHeight;
                    mTranslationX += anchorWidth / 2 - contentWidth / 2;
                    break;
                case LocationStyle.STYLE_RIGHT_BOTTOM:
                    mTranslationY += anchorHeight;
                    mTranslationX += anchorWidth - contentWidth;
                    break;
                default:
                    break;
            }
        }
    }

    private void resolveBundle() {
        final Bundle bundle = getArguments();
        if (bundle != null) {
            mLayoutId = bundle.getInt(KEY_LAYOUT_ID);
            mLocationStyle = bundle.getInt(KEY_STYLE);
        }
    }

    public static class Builder {
        private final GamDialogFragment mCustomDialogFragment;

        public Builder(@LocationStyle int style, @LayoutRes int layoutId) {
            mCustomDialogFragment = GamDialogFragment.newInstance(style, layoutId);
        }

        public Builder setAnchorView(View anchorView) {
            mCustomDialogFragment.mAnchorView = anchorView;
            return this;
        }

        public Builder setOnViewClickListener(View.OnClickListener listener, int... id) {
            mCustomDialogFragment.mOnClickListener = listener;
            mCustomDialogFragment.mClickIds = id;
            return this;
        }

        public GamDialogFragment build() {
            return mCustomDialogFragment;
        }
    }
}