package com.example.pby.gam_study.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.pby.gam_study.network.RxSchedulers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EraserImageView extends View {

    private static final int DEFAULT_SIZE = 100;
    private static final int DEFAULT_PIXEL = 496;
    private Bitmap mBufferBitmap;
    private Canvas mBufferCanvas;
    private Paint mPaint;

    private Bitmap mBitmap;
    private Path mPath;
    private float mLastX;
    private float mLastY;

    private Disposable mDisposable;

    public EraserImageView(Context context) {
        super(context);
        initialize();
    }

    public EraserImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public EraserImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EraserImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    public void setBitmap(Bitmap bitmap) {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        mBitmap = bitmap;
        invalidate();
    }

    public void setBitmapDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            setBitmap(((BitmapDrawable) drawable).getBitmap());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            if (mBufferBitmap == null) {
                mBufferBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                mBufferCanvas = new Canvas(mBufferBitmap);
                Matrix matrix = new Matrix();
                final float ratio = getWidth() * 1.0f / mBitmap.getWidth();
                matrix.setScale(ratio, ratio);
                mBufferCanvas.drawBitmap(mBitmap, matrix, null);
            }
            canvas.drawBitmap(mBufferBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mBitmap == null) {
            return super.onTouchEvent(event);
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                //这里终点设为两点的中心点的目的在于使绘制的曲线更平滑，如果终点直接设置为x,y，效果和lineto是一样的,实际是折线效果
                mPath.quadTo(mLastX, mLastY, (x + mLastX) / 2, (y + mLastY) / 2);
                mPaint.setColor(calculateAvgColor(x, y));
                mBufferCanvas.drawPath(mPath, mPaint);
                invalidate();
                mPath.reset();
                mPath.moveTo(x, y);
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mPath.reset();
                break;
        }
        return true;
    }

    /**
     * 根据当前位置计算大小为{@link #DEFAULT_SIZE}的矩阵内部的平均RGB值
     *
     * @param x
     * @param y
     * @return
     */
    private int calculateAvgColor(int x, int y) {
        x = (int) (x * (mBufferBitmap.getWidth() * 1.0f / getMeasuredWidth()));
        y = (int) (y * (mBufferBitmap.getHeight() * 1.0f / getMeasuredHeight()));
        x = Math.max(0, x - 50);
        if (x + DEFAULT_SIZE > mBitmap.getWidth()) {
            x -= (x + DEFAULT_SIZE) - mBitmap.getWidth();
        }
        y = Math.max(0, y - DEFAULT_SIZE / 2);
        if (y + DEFAULT_SIZE > mBitmap.getHeight()) {
            y -= (y + DEFAULT_SIZE) - mBitmap.getHeight();
        }
        int pixels[] = new int[DEFAULT_SIZE * DEFAULT_SIZE];
        mBufferBitmap.getPixels(pixels, 0, DEFAULT_SIZE, x, y, DEFAULT_SIZE, DEFAULT_SIZE);
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < pixels.length; i++) {
            r += Color.red(pixels[i]);
            g += Color.green(pixels[i]);
            b += Color.blue(pixels[i]);
        }
        r = r / (DEFAULT_SIZE * DEFAULT_SIZE);
        g = g / (DEFAULT_SIZE * DEFAULT_SIZE);
        b = b / (DEFAULT_SIZE * DEFAULT_SIZE);
        return Color.rgb(r, g, b);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @SuppressLint("CheckResult")
    public void save(OnSaveImageListener onSaveImageListener) {
        if (mBufferBitmap == null) {
            return;
        }
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            final Matrix matrix = new Matrix();
            final float ratio = Math.min(DEFAULT_PIXEL * 1.0f / mBufferBitmap.getWidth(), 1);
            // 产生新的bitmap的目的就是为了压缩图片
            // ---
            final Bitmap bitmap = Bitmap.createBitmap((int) (mBufferBitmap.getWidth() * ratio), (int) (mBufferBitmap.getHeight() * ratio), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            matrix.setScale(ratio, ratio);
            canvas.drawBitmap(mBufferBitmap, matrix, null);
            matrix.setScale(mBufferBitmap.getWidth() * ratio / getWidth(), mBufferBitmap.getHeight() * ratio / getHeight());
            canvas.drawBitmap(mBufferBitmap, matrix, null);
            // ---
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(path + File.separator + "demo.png");
            OutputStream ops = new FileOutputStream(file);
            ops.write(bytes);
            ops.close();

        })
                .compose(RxSchedulers.ioToMain())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (onSaveImageListener != null) {
                            onSaveImageListener.onSaveFinished(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onSaveImageListener != null) {
                            onSaveImageListener.onSaveFinished(false);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface OnSaveImageListener {
        void onSaveFinished(boolean result);
    }
}