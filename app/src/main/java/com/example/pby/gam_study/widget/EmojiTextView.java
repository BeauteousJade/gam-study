package com.example.pby.gam_study.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

import com.example.pby.gam_study.network.RxSchedulers;
import com.example.pby.gam_study.util.ExpressionUtil;
import com.example.pby.gam_study.util.StringUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EmojiTextView extends AppCompatTextView {

    public EmojiTextView(Context context) {
        super(context);
    }

    public EmojiTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContent(String content) {
        setText("");
        appendContent(content);
    }

    public void appendContent(String content) {
        if (StringUtil.isEmpty(content)) {
            return;
        }
        final List<String> contentList = ExpressionUtil.contentToStringList(content);
        Observable.create((ObservableOnSubscribe<SpannableStringBuilder>) emitter -> {
            // 初始化为TextView本来的内容
            final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(StringUtil.emptyIfNull(getText()));
            for (String string : contentList) {
                if (emitter.isDisposed()) {
                    emitter.onComplete();
                    return;
                }
                if (string.startsWith("#")) {
                    final String fileName = string.substring(1, string.length() - 1);
                    Drawable drawable = ExpressionUtil.generateDrawable(getContext(), fileName);
                    if (drawable != null) {
                        spannableStringBuilder.append(ExpressionUtil.generateImageSpannableString(drawable, fileName));
                    }
                } else {
                    spannableStringBuilder.append(string);
                }
            }
            // 等所有表情解析完成之后才通知主线程渲染UI
            emitter.onNext(spannableStringBuilder);
        })
                .compose(RxSchedulers.newThreadToMain())
                .subscribe(new Observer<SpannableStringBuilder>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(SpannableStringBuilder spannableStringBuilder) {
                        // 调用setText方法
                        postOnAnimation(() -> setText(spannableStringBuilder));
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
