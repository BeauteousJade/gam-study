package com.example.pby.gam_study.util;

public class ObjectUtil {

    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }
}
