package com.example.pby.gam_study.util.key;

public class KeyboardHeightObserverImpl implements KeyboardHeightObserver {
    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        if (height == 0) {
            onHide();
        } else {
            onShow(height);
        }
    }

    public void onHide() {

    }

    public void onShow(int height) {

    }
}
