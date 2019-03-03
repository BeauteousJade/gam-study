package com.example.pby.gam_study.page.newKind;

import java.util.ArrayList;
import java.util.List;

public class NewKindObservable {
    private List<NewKindObserver> mObserverList = new ArrayList<>();

    public void addObserver(NewKindObserver kindObserver) {
        mObserverList.add(kindObserver);
    }

    public void removeObserver(NewKindObserver kindObserver) {
        mObserverList.remove(kindObserver);
    }

    public void notifyKindName(String kindName) {
        for (NewKindObserver kindObserver : mObserverList) {
            kindObserver.onKindNameChanged(kindName);
        }
    }

    public void notifyKindCover(String cover) {
        for (NewKindObserver kindObserver : mObserverList) {
            kindObserver.onKindCoverChanged(cover);
        }
    }

}
