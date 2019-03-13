package com.example.pby.gam_study.fragment.util;

@FunctionalInterface
public interface Observer {
    void onChanged(String key, Object obj);
}
