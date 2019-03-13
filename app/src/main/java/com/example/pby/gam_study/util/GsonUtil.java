package com.example.pby.gam_study.util;

import com.google.gson.Gson;

public class GsonUtil {

    private static Gson GSON = new Gson();

    public static String toString(Object object) {
        return GSON.toJson(object);
    }

}
