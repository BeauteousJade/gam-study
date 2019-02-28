package com.example.pby.gam_study.util;

import java.util.Collection;

public class ArrayUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isEmpty(T... ts) {
        return ts == null || ts.length == 0;
    }
}
