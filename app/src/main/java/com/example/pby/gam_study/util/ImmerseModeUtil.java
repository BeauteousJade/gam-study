package com.example.pby.gam_study.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.pby.gam_study.R;

public class ImmerseModeUtil {

    public static void setImmerseMode(View titleBar, Context context) {
        final int statusBarHeight = DisplayUtil.getStatusBarHeight(context);
        titleBar.setPadding(titleBar.getPaddingLeft(),
                titleBar.getPaddingTop() + statusBarHeight,
                titleBar.getPaddingRight(),
                titleBar.getPaddingBottom());
        ViewGroup.LayoutParams lp = titleBar.getLayoutParams();
        lp.height = ResourcesUtil.getDimens(context, R.dimen.title_bar_height) + statusBarHeight;
        titleBar.setLayoutParams(lp);
    }
}
