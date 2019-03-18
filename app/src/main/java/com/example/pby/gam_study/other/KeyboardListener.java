package com.example.pby.gam_study.other;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.SoftKeyboardUtils;

public class KeyboardListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private View mDecorView;
    private Activity mActivity;
    private boolean isVisible;
    private int mKeyBoardHeight;

    public KeyboardListener(Activity activity) {
        mActivity = activity;
        mDecorView = activity.getWindow().getDecorView();
    }


    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        mDecorView.getWindowVisibleDisplayFrame(rect);
        int screenHeight = mDecorView.getRootView().getHeight();
        int heightDiff = screenHeight - rect.bottom;
        if (heightDiff > 0 && SoftKeyboardUtils.isSoftShowing(mActivity) && !isVisible) {
            isVisible = true;
            mKeyBoardHeight = heightDiff;
            onShow(mKeyBoardHeight, rect.bottom + DisplayUtil.getStatusBarHeight(mActivity));
        } else if (heightDiff == 0 && !SoftKeyboardUtils.isSoftShowing(mActivity) && isVisible) {
            isVisible = false;
            onHide(mKeyBoardHeight);
        }
    }

    public void onShow(int height, int keyboardTop) {

    }

    public void onHide(int height) {

    }
}
