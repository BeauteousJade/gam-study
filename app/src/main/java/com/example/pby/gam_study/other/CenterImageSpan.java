package com.example.pby.gam_study.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

public class CenterImageSpan extends ImageSpan {

    private int mFontHeight = 0;

    public CenterImageSpan(@NonNull Context context, @NonNull Bitmap bitmap) {
        super(context, bitmap);
    }

    public CenterImageSpan(@NonNull Context context, @NonNull Bitmap bitmap, int verticalAlignment) {
        super(context, bitmap, verticalAlignment);
    }

    public CenterImageSpan(@NonNull Drawable drawable) {
        super(drawable);
    }

    public CenterImageSpan(@NonNull Drawable drawable, int verticalAlignment) {
        super(drawable, verticalAlignment);
    }

    public CenterImageSpan(@NonNull Drawable drawable, @NonNull String source) {
        super(drawable, source);
    }

    public CenterImageSpan(@NonNull Drawable drawable, @NonNull String source, int verticalAlignment) {
        super(drawable, source, verticalAlignment);
    }

    public CenterImageSpan(@NonNull Context context, @NonNull Uri uri) {
        super(context, uri);
    }

    public CenterImageSpan(@NonNull Context context, @NonNull Uri uri, int verticalAlignment) {
        super(context, uri, verticalAlignment);
    }

    public CenterImageSpan(@NonNull Context context, int resourceId) {
        super(context, resourceId);
    }

    public CenterImageSpan(@NonNull Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            //获得文字、图片高度
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;
//            //对于这段算法LZ表示也不解，正常逻辑应该同draw中的计算一样但是显示的结果不居中，经过几次调试之后才发现这么算才会居中
            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;
            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
            mFontHeight = fontHeight;
        }
        if (mFontHeight != 0) {
            d.setBounds(0, 0, mFontHeight, mFontHeight);
        }
        return rect.right;
    }

    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, @NonNull Paint paint) {
        Drawable b = getDrawable();

        canvas.save();
        int transY = 0;
        //获得将要显示的文本高度-图片高度除2等居中位置+top(换行情况)
        transY = ((bottom - top) - b.getBounds().bottom) / 2 + top;
        //偏移画布后开始绘制
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

}
