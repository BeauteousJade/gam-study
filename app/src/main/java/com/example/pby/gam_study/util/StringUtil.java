package com.example.pby.gam_study.util;

import java.util.UUID;

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

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
