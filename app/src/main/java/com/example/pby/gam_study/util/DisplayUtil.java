package com.example.pby.gam_study.util;

import android.content.Context;
import android.util.TypedValue;

public class DisplayUtil {

    public static int getStatusBarHeight(Context context) {
        final int result;
        final int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        } else {
            result = 0;
        }
        return result;
    }

    public static int dpToPx(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
