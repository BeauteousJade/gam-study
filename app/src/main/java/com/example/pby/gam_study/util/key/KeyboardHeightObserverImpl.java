package com.example.pby.gam_study.util.key;

public class KeyboardHeightObserverImpl implements KeyboardHeightObserver {

    private boolean isShow = false;

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        if (height == 0 && isShow) {
            isShow = false;
            onHide();
        } else if (height > 0) {
            isShow = true;
            onShow(height);
        }
    }

    public void onHide() {

    }

    public void onShow(int height) {

    }
}
