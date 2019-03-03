package com.example.pby.gam_study.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    private static SharedPreferences sSharedPreferences;
    private static final String SHARED_PREFERENCES_NAME = "gam-study";

    public static void putInt(Context context, String key, int value) {
        checkInitialize(context);
        sSharedPreferences.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        checkInitialize(context);
        return sSharedPreferences.getInt(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        checkInitialize(context);
        sSharedPreferences.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        checkInitialize(context);
        return sSharedPreferences.getString(key, defValue);
    }

    private static void checkInitialize(Context context) {
        if (sSharedPreferences == null) {
            sSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
    }
}
