package com.example.pby.gam_study.util;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.example.pby.gam_study.R;

import androidx.annotation.StringRes;
import es.dmoral.toasty.Toasty;

public class ToastUtil {

    public static void info(Context context, String text) {
        final Resources resources = context.getResources();
        Toasty.custom(context, text, null, resources.getColor(R.color.color_005), resources.getColor(R.color.white), Toast.LENGTH_SHORT, false, true).show();
    }

    public static void error(Context context, String text) {
        final Resources resources = context.getResources();
        Toasty.custom(context, text, null, resources.getColor(R.color.color_005), resources.getColor(R.color.color_006), Toast.LENGTH_SHORT, false, true).show();
    }

    public static void info(Context context, @StringRes int id) {
        final String text = ResourcesUtil.getString(context, id);
        info(context, text);
    }

    public static void error(Context context, @StringRes int id) {
        final String text = ResourcesUtil.getString(context, id);
        error(context, text);
    }
}
