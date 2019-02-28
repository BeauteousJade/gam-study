package com.example.pby.gam_study.other;


import android.os.Handler;
import android.os.Message;

public class TimeHandler extends Handler {

    private int mInterval;
    private int mRemainTime;
    private TimeHandlerCallback mTimeHandlerCallback;

    private int mStatus = STATUS_DEFAULT;

    private static final int MESSAGE_FLAG = 0;

    private static final int STATUS_DEFAULT = 0;
    private static final int STATUS_START = 1;
    private static final int STATUS_HANDLING = 2;
    private static final int STATUS_STOP = 3;

    public TimeHandler(int interval, int totalTime, TimeHandlerCallback timeHandlerCallback) {
        mInterval = interval;
        mRemainTime = totalTime;
        mTimeHandlerCallback = timeHandlerCallback;
    }

    public void setInterval(int interval) {
        mInterval = interval;
    }

    public void setTotalTime(int totalTime) {
        mRemainTime = totalTime;
    }

    public void start() {
        if (mRemainTime < mInterval) {
            return;
        }
        if (mTimeHandlerCallback == null) {
            return;
        }
        mStatus = STATUS_START;
        mTimeHandlerCallback.onStart();
        sendMessage();
    }

    public void stop() {
        switch (mStatus) {
            case STATUS_START:
            case STATUS_HANDLING:
                // 移除掉message，避免内存泄漏
                removeMessages(MESSAGE_FLAG);
                mTimeHandlerCallback.onStop();
                break;
            case STATUS_STOP:
            default:
                break;

        }
    }

    private void sendMessage() {
        sendEmptyMessageDelayed(MESSAGE_FLAG, mInterval);
    }

    @Override
    public void handleMessage(Message msg) {
        mRemainTime -= mInterval;
        mTimeHandlerCallback.onHandle(mRemainTime);
        if (mRemainTime <= 0) {
            // stop方法必须在mStatus的赋值之前执行，保证onStop方法只执行一次
            stop();
            mStatus = STATUS_STOP;
        } else {
            mStatus = STATUS_HANDLING;
            sendMessage();
        }
    }

    public interface TimeHandlerCallback {
        /**
         * 当 {@link TimeHandler} 开始运行时，会回调此方法。
         * 此方法只会调用一次。
         */
        void onStart();

        /**
         * 当 {@link TimeHandler} 正在运行时，会回调此方法。
         *
         * @param remainTime 剩余的时间。如果不出意外的话，此时此方法还会调用remainTime / {@link #mInterval}次。
         */
        void onHandle(int remainTime);

        /**
         * 当 {@link TimeHandler} 结束运行时，会回调此方法。
         * 此方法只会调用一次。
         */
        void onStop();
    }
}
