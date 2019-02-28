package com.example.pby.gam_study.page.login.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.other.TimeHandler;
import com.example.pby.gam_study.util.ObjectUtil;
import com.example.pby.gam_study.util.StringUtil;

@SuppressLint("AppCompatCustomView")
public class CountDownTextView extends TextView {

    private static final int INTERVAL = 1000;
    private static final int TOTAL_TIME = 120000;

    private String mOriginText;
    private String mCountDownRegexText;

    private final TimeHandler mTimeHandler = new TimeHandler(INTERVAL, TOTAL_TIME, new TimeHandler.TimeHandlerCallback() {
        @Override
        public void onStart() {
            final String mCountDownText = String.format(mCountDownRegexText, TOTAL_TIME / 1000);
            setText(mCountDownText);
        }

        @Override
        public void onHandle(int remainTime) {
            final String mCountDownText = String.format(mCountDownRegexText, remainTime / 1000);
            setText(mCountDownText);
        }

        @Override
        public void onStop() {
            setText(mOriginText);
            enableCountDown(true);
        }
    });

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    public void setOriginText(String originText) {
        mOriginText = originText;
        setText(originText);
    }

    public String getOriginText() {
        return mOriginText;
    }

    public void setCountDownRegexText(String countDownRegexText) {
        mCountDownRegexText = countDownRegexText;
    }

    public String getCountDownRegexText() {
        return mCountDownRegexText;
    }

    public void start() {
        if (!ObjectUtil.equals(mOriginText, mCountDownRegexText) && !StringUtil.isEmpty(mOriginText) && !StringUtil.isEmpty(mCountDownRegexText)) {
            mTimeHandler.setInterval(INTERVAL);
            mTimeHandler.setTotalTime(TOTAL_TIME);
            mTimeHandler.start();
        }
    }

    public void stop() {
        mTimeHandler.stop();
    }

    public void enableCountDown(boolean enable) {
        setEnabled(enable);
        if (enable) {
            setBackground(getResources().getDrawable(R.drawable.bg_validate_code_textview_click));
            setTextColor(getResources().getColor(R.color.white));
        } else {
            setBackground(getResources().getDrawable(R.drawable.bg_validate_code_textview_unclick));
            setTextColor(getResources().getColor(R.color.color_004));
        }
    }
}
