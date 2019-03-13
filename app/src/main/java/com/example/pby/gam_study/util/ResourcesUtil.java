package com.example.pby.gam_study.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class ResourcesUtil {

    public static Drawable getDrawable(Context context, @DrawableRes int id) {
        return context.getResources().getDrawable(id);
    }

    @ColorInt
    public static int getColor(Context context, @ColorRes int id) {
        return context.getResources().getColor(id);
    }

    public static String getString(Context context, @StringRes int id) {
        return context.getResources().getString(id);
    }

    public static int getDimens(Context context, @DimenRes int id) {
        return context.getResources().getDimensionPixelSize(id);
    }
}
