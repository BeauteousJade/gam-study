package com.example.pby.gam_study.util;

public class StringUtil {

    public static boolean isEmpty(String string) {
        return string == null || string.equals("");
    }

    public static String emptyIfNull(String string) {
        if (string == null) {
            return "";
        }
        return string;
    }
}
