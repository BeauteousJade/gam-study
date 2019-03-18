package com.example.pby.gam_study.fragment.util;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Observer> mObserverList = new ArrayList<>();

    public void addObserver(Observer observer) {
        mObserverList.add(observer);
    }

    public void removeObserver(Observer observer) {
        mObserverList.remove(observer);
    }

    public void notifyChanged(String key, Object value) {
        for (Observer observer : mObserverList) {
            observer.onChanged(key, value);
        }
    }
}
