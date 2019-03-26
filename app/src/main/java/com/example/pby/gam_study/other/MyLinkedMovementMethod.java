package com.example.pby.gam_study.other;

import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

public class MyLinkedMovementMethod extends LinkMovementMethod {
    private static MyLinkedMovementMethod sInstance;
    private long mDownTime;


    public static MyLinkedMovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new MyLinkedMovementMethod();
        return sInstance;
    }

    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mDownTime = System.currentTimeMillis();
        }
        if (action == MotionEvent.ACTION_UP) {
            long interval = System.currentTimeMillis() - mDownTime;
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (interval < ViewConfiguration.getLongPressTimeout()) {
                    link[0].onClick(widget);
                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                            buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                return true;
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }
}