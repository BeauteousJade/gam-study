package com.example.pby.gam_study.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.ResourcesUtil;

public class TitleBar extends ConstraintLayout {

    private static final int ID_TITLE = R.id.title;
    private static final int ID_LEFT_ICON = R.id.left_icon;
    private static final int ID_RIGHT_ICON = R.id.right_icon;

    private String mTitle;
    @DrawableRes
    private int mLeftIcon;
    @DrawableRes
    private int mRightIcon;
    private View mLeftView;
    private View mTitleView;
    private View mRightView;

    private void initIcon(int icon, View view) {
        if (view instanceof ImageView && icon > 0) {
            ((ImageView) view).setImageDrawable(ResourcesUtil.getDrawable(getContext(), icon));
        }
    }

    public void setTitle(String title) {
        if (mTitleView == null) {
            mTitleView = findViewById(ID_TITLE);
        }
        mTitle = title;
        if (mTitleView instanceof TextView) {
            ((TextView) mTitleView).setText(mTitle);
        }
    }

    public void setLeftIcon(@DrawableRes int leftIcon) {
        if (mLeftView == null) {
            mLeftView = findViewById(ID_LEFT_ICON);
        }
        mLeftIcon = leftIcon;
        initIcon(leftIcon, mLeftView);
    }

    public void setRightIcon(@DrawableRes int rightIcon) {
        if (mRightView == null) {
            mRightView = findViewById(ID_RIGHT_ICON);
        }
        mRightIcon = rightIcon;
        initIcon(rightIcon, mRightView);
    }

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
