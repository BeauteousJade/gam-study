package com.example.pby.gam_study.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.example.pby.gam_study.network.RxSchedulers;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.ExpressionUtil;
import com.example.pby.gam_study.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatEditText;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EmojiEditText extends AppCompatEditText {

    private Disposable mSetContentDisposable;
    private List<Disposable> mAppendContentDisposableList = new ArrayList<>();

    public EmojiEditText(Context context) {
        super(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mSetContentDisposable != null) {
            mSetContentDisposable.dispose();
        }
        if (!ArrayUtil.isEmpty(mAppendContentDisposableList)) {
            for (Disposable disposable : mAppendContentDisposableList) {
                disposable.dispose();
            }
        }
    }


    public void setContent(String content) {
        setText("");
        if (StringUtil.isEmpty(content)) {
            return;
        }
        final List<String> contentList = ExpressionUtil.contentToStringList(content);
        Observable.create(emitter -> {
            for (String string : contentList) {
                if (emitter.isDisposed()) {
                    emitter.onComplete();
                    return;
                }
                if (string.startsWith("#")) {
                    final String fileName = string.substring(1, string.length() - 1);
                    Drawable drawable = ExpressionUtil.generateDrawable(getContext(), fileName);
                    if (drawable != null) {
                        emitter.onNext(ExpressionUtil.generateImageSpannableString(drawable, fileName));
                    } else {
                        emitter.onError(new NullPointerException());
                    }
                } else {
                    emitter.onNext(string);
                }
            }
        })
                .compose(RxSchedulers.ioToMain())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mSetContentDisposable = d;
                    }

                    @Override
                    public void onNext(Object o) {
                        if (o instanceof SpannableString) {
                            append((SpannableString) o);
                        } else {
                            append(o.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void appendExpression(String fileName) {
        Observable.create((ObservableOnSubscribe<Drawable>) emitter -> {
            if (!emitter.isDisposed()) {
                Drawable drawable = ExpressionUtil.generateDrawable(getContext(), fileName);
                if (drawable != null) {
                    emitter.onNext(drawable);
                } else {
                    emitter.onError(new NullPointerException());
                    return;
                }
                emitter.onComplete();
            }
        })
                .compose(RxSchedulers.ioToMain())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mAppendContentDisposableList.add(d);
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        append(ExpressionUtil.generateImageSpannableString(drawable, fileName));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return super.onKeyPreIme(keyCode, event);
    }
}


