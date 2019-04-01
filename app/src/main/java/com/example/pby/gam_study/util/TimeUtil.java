package com.example.pby.gam_study.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {

    public static int differentDays(long time1, long time2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(time2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            return day2 - day1;
        }
    }

    public static int differentYears(long time1, long time2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(time2);
        return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
    }


    @SuppressLint("SimpleDateFormat")
    public static String formatTime(long time, String pattern) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern(pattern);
        return simpleDateFormat.format(time);
    }


    public static String formatTime(long time) {
        final long time1 = time;
        final long time2 = System.currentTimeMillis();
        final int dDay = TimeUtil.differentDays(time1, time2);
        final int dYear = TimeUtil.differentYears(time1, time2);
        if (dDay == 0) {
            return formatTime(time1, "今天 HH:mm");
        } else if (dDay == 1) {
            return formatTime(time1, "昨天 HH:mm");
        } else if (dDay == 2) {
            return formatTime(time1, "前天 HH:mm");
        } else if (dYear == 0) {
            return formatTime(time1, "MM:dd HH:mm");
        } else {
            return formatTime(time1, "yyyy:MM:dd");
        }
    }
}
