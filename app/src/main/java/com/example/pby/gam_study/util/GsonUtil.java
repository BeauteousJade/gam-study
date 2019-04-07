package com.example.pby.gam_study.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtil {

    private static Gson GSON = new Gson();

    public static String toString(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
