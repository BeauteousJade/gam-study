package com.example.pby.gam_study.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;

import com.example.pby.gam_study.other.CenterImageSpan;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionUtil {

    public static Drawable generateDrawable(Context context, String fileName) {
        final AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("expression/" + fileName);
            return Drawable.createFromStream(inputStream, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将正常String字符串分割为String数组。格式如下
     * 假设：
     * content为123#123#123，分割之后的数组为[123,#123#,123]
     *
     * @param content
     * @return
     */
    public static List<String> contentToStringList(String content) {
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

    public static SpannableString generateImageSpannableString(Drawable drawable, String fileName) {
        final String regexString = "#" + fileName + "#";
        CenterImageSpan imageSpan = new CenterImageSpan(drawable, regexString);
        SpannableString spannableString = new SpannableString(regexString);
        spannableString.setSpan(imageSpan, 0, regexString.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
