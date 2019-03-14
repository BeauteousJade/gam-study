package com.example.pby.gam_study.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.util.AttributeSet;

import com.example.pby.gam_study.network.RxSchedulers;
import com.example.pby.gam_study.other.CenterImageSpan;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import androidx.annotation.WorkerThread;
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


    public void appendExpression(String fileName) {
        Observable.create((ObservableOnSubscribe<Drawable>) emitter -> {
            if (!emitter.isDisposed()) {
                Drawable drawable = generateDrawable(fileName);
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
                        append(generateImageSpannableString(drawable, fileName));
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

    private SpannableString generateImageSpannableString(Drawable drawable, String fileName) {
        CenterImageSpan imageSpan = new CenterImageSpan(drawable, fileName);
        SpannableString spannableString = new SpannableString(fileName);
        spannableString.setSpan(imageSpan, 0, fileName.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @WorkerThread
    private Drawable generateDrawable(String fileName) {
        final AssetManager assetManager = getContext().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("expression/" + fileName);
            return Drawable.createFromStream(inputStream, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setContent(String content) {
        if (StringUtil.isEmpty(content)) {
            setText("");
            return;
        }
        final List<String> contentList = contentToStringList(content);
        Observable.create(emitter -> {
            for (String string : contentList) {
                if (mSetContentDisposable != null && mSetContentDisposable.isDisposed()) {
                    emitter.onComplete();
                    return;
                }
                if (string.startsWith("#")) {
                    Drawable drawable = generateDrawable(string.substring(1, string.length() - 1));
                    if (drawable != null) {
                        emitter.onNext(drawable);
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


    /**
     * 将正常String字符串分割为String数组。格式如下
     * 假设：
     * content为123#123#123，分割之后的数组为[123,#123#,123]
     *
     * @param content
     * @return
     */
    private List<String> contentToStringList(String content) {
        char[] chars = content.toCharArray();
        List<String> list = new ArrayList<>();
        // 普通字符
        Stack<Character> stack1 = new Stack<>();
        // #
        Stack<Character> stack2 = new Stack<>();

        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            if (c == '#' || i == chars.length - 1) {
                if (c == '#') {
                    stack2.push(c);
                } else {
                    stack1.push(c);
                }
                if (!stack1.isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (!stack1.isEmpty()) {
                        stringBuilder.insert(0, stack1.pop());
                    }
                    if (stack2.size() == 2) {
                        stack2.clear();
                        stringBuilder.insert(0, "#");
                        stringBuilder.append("#");
                    }
                    list.add(stringBuilder.toString());
                }
            } else {
                stack1.push(c);
            }
        }
        return list;
    }
}
