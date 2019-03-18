package com.example.pby.gam_study.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.other.CenterImageSpan;
import com.example.pby.gam_study.util.ResourcesUtil;

import androidx.appcompat.widget.AppCompatTextView;

public class LikeTextView extends AppCompatTextView {
    public LikeTextView(Context context) {
        super(context);
    }

    public LikeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LikeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void appendLikeUser(String userName) {
        final String icon = "icon";
        SpannableString spannableString = new SpannableString(icon + userName);
        spannableString.setSpan(getImageSpan(), 0, icon.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(getForegroundColorSpan(), icon.length(), spannableString.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        append(spannableString);
    }

    private CenterImageSpan getImageSpan() {
        return new CenterImageSpan(ResourcesUtil.getDrawable(getContext(), R.mipmap.icon_like));
    }

    private ForegroundColorSpan getForegroundColorSpan() {
        return new ForegroundColorSpan(ResourcesUtil.getColor(getContext(), R.color.color_main_blue));
    }
}
