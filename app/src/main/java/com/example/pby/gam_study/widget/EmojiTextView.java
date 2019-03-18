package com.example.pby.gam_study.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.util.AttributeSet;

import com.example.pby.gam_study.network.RxSchedulers;
import com.example.pby.gam_study.util.ExpressionUtil;
import com.example.pby.gam_study.util.StringUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EmojiTextView extends AppCompatTextView {

    private Disposable mDisposable;


    public EmojiTextView(Context context) {
        super(context);
    }

    public EmojiTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDisposable != null) {
            mDisposable.dispose();
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
                        mDisposable = d;
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
}
