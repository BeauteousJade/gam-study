package com.example.pby.gam_study.util;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class StringUtil {

    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    public static CharSequence emptyIfNull(CharSequence string) {
        if (string == null) {
            return "";
        }
        return string;
    }

    public static String formatTime(long time) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yyyy-mm-dd  HH:mm:ss");
        return simpleDateFormat.format(new Date(time));
    }
}
